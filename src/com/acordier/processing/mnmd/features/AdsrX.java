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

	public float getAmp() {
		return amp;
	}

	public void setAmp(float amp) {
		this.amp = amp;
		updateParameters();
		
	}

	public float getAtt() {
		return att;
	}

	public void setAtt(float att) {
		this.att = att;
		updateParameters();
	}

	public float getDec() {
		return dec;
	}

	public void setDec(float dec) {
		this.dec = dec;
		updateParameters();
	}

	public float getSus() {
		return sus;
	}

	public void setSus(float sus) {
		this.sus = sus;
		updateParameters();
	}

	public float getRel() {
		return rel;
	}

	public void setRel(float rel) {
		this.rel = rel;
		updateParameters();
	}
	
	private void updateParameters(){
		super.setParameters(amp, att, dec, sus, rel, 0, 0.0F);
	}
	
	
	
}
