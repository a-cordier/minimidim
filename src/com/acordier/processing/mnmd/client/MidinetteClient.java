package com.acordier.processing.mnmd.client;

import processing.core.PApplet;

import com.acordier.processing.mnmd.features.Midinette;
import com.acordier.processing.mnmd.features.Youki;

import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Knob;

public class MidinetteClient extends PApplet {

	private static final long serialVersionUID = 1L;
	ControlP5 cP5;
	Knob filterKnob;
	
	@Override
	public void setup() {
		size(400, 300);
		cP5 = new ControlP5(this);
		final Youki youki = new Youki(this);
		Midinette midinette = new Midinette(youki);
		midinette.randomize(3);
		//midinette.fourBeat(60);
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
	                setPosition(25,25).
	                setRadius(25).
	                setDragDirection(Knob.VERTICAL).
	                setDragDirection(Knob.DOWN).addListener(new ControlListener() {
						
						@Override
						public void controlEvent(ControlEvent event) {
							float value = map(event.getValue(), 0, 127, 100, 1200);
							System.out.println(value);
							youki.getFilter().frequency.setLastValue(value); 
							
						}
					});

	}

	@Override
	public void draw() {
		background(255);


	}


}
