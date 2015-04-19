package com.acordier.processing.mnmd.client;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import com.acordier.processing.mnmd.features.Midinette;
import com.acordier.processing.mnmd.features.Youki;

import controlP5.Canvas;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Knob;
import controlP5.RadioButton;
import controlP5.Slider;
import ddf.minim.AudioOutput;
import ddf.minim.ugens.Waves;

public class MidinetteClient extends PApplet {

	private static final long serialVersionUID = 1L;
	ControlP5 cP5;
	Knob filterKnob, resKnob;
	RadioButton vco1Wave, vco2Wave;
	Slider attackSlider, decaySlider, sustainSlider, releaseSlider;
	Canvas adsrCanvas;
	List<CheckBox> stepSequencer;
	final Youki youki = new Youki(this);
	
	@Override
	public void setup() {

		size(400, 300);
		cP5 = new ControlP5(this);

		Midinette midinette = new Midinette(youki);
		stepSequencer = new ArrayList<CheckBox>(midinette.getStepSequence()
				.size());
		midinette.randomize(3);
		midinette.play(true);

		filterKnob = cP5.addKnob("filter").setRange(0, 127)
				.setViewStyle(Knob.ARC).setConstrained(true).setValue(75)
				.setPosition(5, 5).setRadius(20)
				.setDragDirection(Knob.VERTICAL)
				.addListener(new ControlListener() {

					@Override
					public void controlEvent(ControlEvent event) {
						float value = map(event.getValue(), 0, 127, 50, 2200);
						System.out.println("freq: " + value);
						youki.getFilter().frequency.setLastValue(value);
						youki.getLFO().offset.setLastValue(2000 + value);

					}
				});
		filterKnob.setValue(map(youki.getFilter().frequency.getLastValue(), 50, 2200, 0, 127));
		filterKnob.setCaptionLabel("Filter");
		filterKnob.setLabelVisible(true);
		filterKnob.setColorCaptionLabel(0);
		filterKnob.getValueLabel().hide();

		resKnob = cP5.addKnob("res").setRange(0, 127).setViewStyle(Knob.ARC)
				.setConstrained(true).setValue(75).setPosition(60, 5)
				.setRadius(20).setDragDirection(Knob.VERTICAL)
				.addListener(new ControlListener() {

					@Override
					public void controlEvent(ControlEvent event) {
						float value = map(event.getValue(), 0, 127, 0.F, 0.85F);
						System.out.println("res: " + value);
						youki.getFilter().resonance.setLastValue(value);
					}
				});
		resKnob.setValue(map(youki.getFilter().resonance.getLastValue(), 0.F, 0.85F, 0, 127));
		resKnob.setCaptionLabel("Res.");
		resKnob.setLabelVisible(true);
		resKnob.setColorCaptionLabel(0);
		resKnob.getValueLabel().hide();
		
		attackSlider = cP5.addSlider("attack", 0, 127, 200, height - 100, 10,
				100);
		attackSlider.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent event) {
				System.out.println("attack: " + event.getValue());
				youki.getAdsr().setAtt(
						map(event.getValue(), 0, 127, 0.00001F, 0.25F));
			}
		});
		attackSlider.setValue(map(youki.getAdsr().getAtt(),0.00001F, 0.25F, 0,127));


		decaySlider = cP5
				.addSlider("decay", 0, 127, 211, height - 100, 10, 100);
		decaySlider.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent event) {
				System.out.println("decay: " + event.getValue());
				youki.getAdsr().setDec(
						map(event.getValue(), 0, 127, 0.01F, 0.25F));
			}
		});
		decaySlider.setValue(map(youki.getAdsr().getDec(), 0.01F, 0.25F, 0, 127));

		sustainSlider = cP5.addSlider("sustain", 0, 127, 222, height - 100, 10,
				100);
		sustainSlider.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent event) {
				System.out.println("sustain: " + event.getValue());
				youki.getAdsr().setSus(
						map(event.getValue(), 0, 127, 0.0001F, 1.F));
			}
		});
		sustainSlider.setValue(map(youki.getAdsr().getSus(),0.0001F, 1.F, 0, 127));

		releaseSlider = cP5.addSlider("release", 0, 127, 233, height - 100, 10,
				100);
		releaseSlider.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent event) {
				System.out.println("release: " + event.getValue());
				youki.getAdsr().setRel(
						map(event.getValue(), 0, 127, 0.0001F, 1.F));
			}
		});
		releaseSlider.setValue(map(youki.getAdsr().getRel(),  0.0001F, 1.F, 0, 127));
		
		vco1Wave = cP5.addRadioButton("vco_1")
				.setPosition(0, height - 5 * (10 + 1) - 2).setSize(10, 10)
				.setColorForeground(color(120))
				.setColorActive(color(255, 50, 50)).setColorLabel(color(0))
				.setItemsPerRow(1).setSpacingColumn(50)
				.addItem("vco_1_sine", 1).addItem("vco_1_tri", 2)
				.addItem("vco_1_saw", 3).addItem("vco_1_square", 4)
				.addItem("vco_1_pwm", 5).activate(0).activateEvent(true);

		vco1Wave.getItem(0).setCaptionLabel("Sine");
		vco1Wave.getItem(1).setCaptionLabel("Tri");
		vco1Wave.getItem(2).setCaptionLabel("Saw");
		vco1Wave.getItem(3).setCaptionLabel("Square");
		vco1Wave.getItem(4).setCaptionLabel("PWM");

		vco1Wave.getItem(0).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO1().setWaveform(Waves.SINE);

			}
		});
		vco1Wave.getItem(1).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO1().setWaveform(Waves.TRIANGLE);

			}
		});
		vco1Wave.getItem(2).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO1().setWaveform(Waves.SAW);

			}
		});
		vco1Wave.getItem(3).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO1().setWaveform(Waves.SQUARE);

			}
		});
		vco1Wave.getItem(4).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO1().setWaveform(Waves.QUARTERPULSE);

			}
		});

		vco2Wave = cP5.addRadioButton("vco_2")
				.setPosition(50, height - 5 * (10 + 1) - 2).setSize(10, 10)
				.setColorForeground(color(120))
				.setColorActive(color(255, 50, 50)).setColorLabel(color(0))
				.setItemsPerRow(1).setSpacingColumn(50)
				.addItem("vco_2_sine", 1).addItem("vco_2_tri", 2)
				.addItem("vco_2_saw", 3).addItem("vco_2_square", 4)
				.addItem("vco_2_pwm", 5).activate(0).activateEvent(true);

		vco2Wave.getItem(0).setCaptionLabel("Sine");
		vco2Wave.getItem(1).setCaptionLabel("Tri");
		vco2Wave.getItem(2).setCaptionLabel("Saw");
		vco2Wave.getItem(3).setCaptionLabel("Square");
		vco2Wave.getItem(4).setCaptionLabel("PWM");

		vco2Wave.getItem(0).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO2().setWaveform(Waves.SINE);

			}
		});
		vco2Wave.getItem(1).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO2().setWaveform(Waves.TRIANGLE);

			}
		});
		vco2Wave.getItem(2).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO2().setWaveform(Waves.SAW);

			}
		});
		vco2Wave.getItem(3).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO2().setWaveform(Waves.SQUARE);

			}
		});

		vco2Wave.getItem(4).addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent arg0) {
				youki.getVCO2().setWaveform(Waves.QUARTERPULSE);

			}
		});

	}

	@Override
	public void draw() {
		AudioOutput out = youki.getAudioOut();
		background(255);
		// draw the waveforms
		for (int i = 0; i < out.bufferSize() - 1; i++) {
			// find the x position of each buffer value
			float x1 = map(i, 0, out.bufferSize(), 0, width);
			float x2 = map(i + 1, 0, out.bufferSize(), 0, width);
			// draw a line from one buffer position to the next for both
			// channels
			line(x1, 150 + out.left.get(i) * 100, x2,
					150 + out.left.get(i + 1) * 100);
			line(x1, 150 + out.right.get(i) * 100, x2,
					150 + out.right.get(i + 1) * 100);
		}

 	}
}
