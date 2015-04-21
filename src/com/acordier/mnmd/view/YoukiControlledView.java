package com.acordier.mnmd.view;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import com.acordier.mnmd.controller.YoukiViewController;
import com.acordier.processing.mnmd.features.Midinette;
import com.acordier.processing.mnmd.features.Youki;

import controlP5.CP;
import controlP5.Canvas;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Knob;
import controlP5.RadioButton;
import controlP5.Slider;
import ddf.minim.AudioOutput;
import ddf.minim.ugens.Waves;

public class YoukiControlledView extends PApplet {

	private static final long serialVersionUID = 1L;
	private final int SK_WIDTH = 400, SK_HEIGHT = 300;
	ControlP5 cP5;
	Knob filterKnob, resKnob;
	RadioButton vco1Wave, vco2Wave;
	Knob gainKnob;
	Slider ampSlider, attackSlider, decaySlider, sustainSlider, releaseSlider;
	Canvas adsrCanvas;
	List<CheckBox> stepSequencer;
	final Youki youki = new Youki(this);
	YoukiViewController viewController;
	
	@Override
	public void setup() {
		background(255);
		size(SK_WIDTH, SK_HEIGHT);
		cP5 = new ControlP5(this);
		Midinette midinette = new Midinette(youki);
		stepSequencer = new ArrayList<CheckBox>(midinette.getStepSequence()
				.size());
		midinette.randomize(3);
		midinette.play(true);
		
		viewController = new YoukiViewController(youki);
		/* FITLER KNOB VIEW */
		filterKnob = buildKnobView("Filter");
		filterKnob.setPosition(5, 5);
		viewController.bindFilter(filterKnob);
		
		/* RES KNOB VIEW */
		resKnob = buildKnobView("Res.");
		resKnob.setPosition(60, 5);
		viewController.bindResonance(resKnob);
		
		/* ADSR VIEW */
		Group adsrWidget = buildAdsrView("ADSR");
		adsrWidget.setPosition(100, height-adsrWidget.getHeight());
		viewController.bindAdsr(adsrWidget);
		/* ADSR VIEW DONE */
		
		/* VCO 1 WAVE SELECTOR VIEW */
		vco1Wave = buildWaveSelectorView("vco_1");
		vco1Wave.setPosition(0, height - 5 * (10 + 1) - 2);
		viewController.bindWaveSelector(vco1Wave);

		/* VCO 1 WAVE SELECTOR VIEW  DONE */
		
		/* VCO 2 WAVE SELECTOR VIEW */
		vco2Wave = buildWaveSelectorView("vco_2");
		vco2Wave.setPosition(50, height - 5 * (10 + 1) - 2);
		viewController.bindWaveSelector(vco2Wave);
		
		/* GAIN ROTARY KNOB VIEW */
		gainKnob = buildKnobView("Gain");
		gainKnob.setPosition(120, 5);
		viewController.bindGain(gainKnob);

		/* WAVE VISUALISATION VIEW */
		Canvas canvas = new Canvas() {
			float width = SK_WIDTH, height = 57, cX = 170, cY = SK_HEIGHT-height;
			@Override
			public void draw(PApplet arg0) {
				background(255);
				noStroke();
				rect(cX, cY, width, height);
				stroke(cP5.getColor().getBackground());
				AudioOutput out = youki.getAudioOut();
				// draw the waveforms
				for (int i = 0; i < out.bufferSize() - 1; i++) {
					// find the x position of each buffer value
					float x1 = map(i, 0, out.bufferSize(), cX, cX+width);
					float x2 = map(i + 1, 0, out.bufferSize(), cX, cX+width);
					float y1 = map(out.left.get(i), 0, 1, cY+height/2,  cY+height);
					float y2 = map(out.left.get(i+1), 0, 1, cY+height/2,  cY+height);
					line(x1, y1, x2,
							y2);
					line(x1, y1, x2,
							y2);
				}
				
			}
		};
		canvas.setMode(Canvas.PRE);
		cP5.addCanvas(canvas);
		/* WAVE VISUALISATION VIEW DONE */

	}

	@Override
	public void draw() {
		

 	}
	
	private Knob buildKnobView(String name){
		Knob knob = cP5.addKnob(name);
		knob.setRange(0, 127);
		knob.setViewStyle(Knob.ARC);
		knob.setConstrained(true);
		knob.setRadius(20);
		knob.setDragDirection(Knob.VERTICAL);
		knob.setCaptionLabel(name);
		knob.setLabelVisible(true);
		knob.setColorCaptionLabel(0);
		knob.getColor().setActive(color(255, 50, 50));
		knob.getValueLabel().hide();
		return knob;
	}
	
	private Group buildAdsrView(String name){
		String[]modules= {"Amp.","Att.","Dec.","Sus.","Rel."};
		Group out = cP5.addGroup(name);
		out.setBackgroundColor(color(255,50)).hideBar();
		int sliderHeight = 57;
		int sliderWidth = 10;
		int x =0, y=0;
		for(String module:modules){
			Slider slider = cP5.addSlider(module);
			slider.setMin(0).setMax(127);
			slider.setWidth(sliderWidth).setHeight(sliderHeight);
			slider.setPosition(x, y);
			slider.setGroup(out);
			x += sliderWidth + 1;
		}	
		out.setWidth((sliderWidth+1) * modules.length);
		out.setHeight(sliderHeight);
		return out;
	}
	
	private RadioButton buildWaveSelectorView(String name){
		String[]waves = {"Sine","Tri.", "Saw", "Square", "PWM"};
		RadioButton out = cP5.addRadioButton(name);
		out.setSize(10, 10);
		out.setColorForeground(color(120));
		out.setColorActive(color(255, 50, 50));
		out.setColorLabel(color(0));
		out.setItemsPerRow(1);
		out.setSpacingColumn(50);
		out.activateEvent(true);
		out.setNoneSelectedAllowed(false);
		for(int i = 0; i < waves.length; i++){
			out.addItem(name+waves[i], i);
			out.getItem(i).setCaptionLabel(waves[i]);
		
		}
		return out;
	}
}
