package com.acordier.processing.mnmd.client;

import processing.core.PApplet;

import com.acordier.processing.mnmd.core.Midinette;
import com.acordier.processing.mnmd.core.Youki;

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
