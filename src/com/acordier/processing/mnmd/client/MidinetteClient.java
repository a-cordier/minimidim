package com.acordier.processing.mnmd.client;

import com.acordier.processing.mnmd.core.Midinette;
import com.acordier.processing.mnmd.core.Youki;
import com.acordier.processing.mnmd.model.ScalesConstants;

import processing.core.PApplet;

public class MidinetteClient extends PApplet {

	private static final long serialVersionUID = 1L;


	
	@Override
	public void setup() {
		Youki youki = new Youki(this);
		Midinette midinette = new Midinette(youki);
		midinette.randomize();
		midinette.play(true);
	}
	
	@Override
	public void draw() {

	}



}
