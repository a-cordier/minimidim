package com.acordier.processing.mnmd.client;

import com.acordier.processing.mndmd.core.Midinette;
import com.acordier.processing.mndmd.core.Youki;

import processing.core.PApplet;

public class MidinetteClient extends PApplet {

	private static final long serialVersionUID = 1L;


	
	@Override
	public void setup() {
		size(100,100);
		Youki youki = new Youki(this);
		Midinette midinette = new Midinette(youki);
		midinette.randomize();
		midinette.play(true);
	}
	
	@Override
	public void draw() {

	}



}
