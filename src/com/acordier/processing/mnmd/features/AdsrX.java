package com.acordier.processing.mnmd.features;

import ddf.minim.ugens.ADSR;

/**
 * AdsrX extends ADSR to ease the 
 * way parameters are accessed from
 * the view 
 * @author acordier
 *
 */
public class AdsrX extends ADSR {
	
	private float amp, att, dec, sus, rel;
	
	public AdsrX(float amp, float att, float dec, float sus, float rel) {
		super(amp, att, dec, sus, rel);
		this.amp = amp;
		this.att = att;
		this.dec = dec;
		this.sus = sus;
		this.rel = rel;
	}

	public float getAmplitude() {
		return amp;
	}

	public void setAmplitude(float amp) {
		this.amp = amp;
		updateParameters();
		
	}

	public float getAttack() {
		return att;
	}

	public void setAttack(float att) {
		this.att = att;
		updateParameters();
	}

	public float getDecay() {
		return dec;
	}

	public void setDecay(float dec) {
		this.dec = dec;
		updateParameters();
	}

	public float getSustain() {
		return sus;
	}

	public void setSustain(float sus) {
		this.sus = sus;
		updateParameters();
	}

	public float getRelease() {
		return rel;
	}

	public void setRelease(float rel) {
		this.rel = rel;
		updateParameters();
	}
	
	private void updateParameters(){
		super.setParameters(amp, att, dec, sus, rel, 0, 0.0F);
	}
	
	
	
}
