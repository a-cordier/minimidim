package com.acordier.processing.mnmd.client;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import com.acordier.processing.mnmd.features.Midinette;
import com.acordier.processing.mnmd.features.Youki;

import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Knob;

public class MidinetteClient extends PApplet {

	private static final long serialVersionUID = 1L;
	ControlP5 cP5;
	Knob filterKnob;
	List<CheckBox> stepSequencer;
	
	@Override
	public void setup() {
		size(100, 100);
		cP5 = new ControlP5(this);
		final Youki youki = new Youki(this);
		Midinette midinette = new Midinette(youki);
		stepSequencer = new ArrayList<CheckBox>(midinette.getStepSequence().size());
		midinette.randomize(3);
		midinette.play(true);
		filterKnob = cP5.addKnob("filter").
					setRange(0,127).
					setNumberOfTickMarks(127).
					setTickMarkLength(1).
					hideTickMarks().
					snapToTickMarks(true).
					setViewStyle(Knob.ARC).
					setConstrained(true).
	                setValue(75).
	                setPosition(0,0).
	                setRadius(50).
	                setDragDirection(Knob.VERTICAL).
	                setDragDirection(Knob.DOWN).addListener(new ControlListener() {
						
						@Override
						public void controlEvent(ControlEvent event) {
							float value = map(event.getValue(), 0, 127, 100, 1600);
							System.out.println(value);
							//youki.getLFO().unpatch(youki.getFilter());
							youki.getFilter().frequency.setLastValue(value);
							//youki.getLFO().offset.setLastValue(2000.F); // i don't know what i'm doing here
							//youki.getLFO().patch(youki.getFilter().frequency);
							
						}
					});
	}

	@Override
	public void draw() {
		background(255);
	}
	
}
