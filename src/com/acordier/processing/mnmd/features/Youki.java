package com.acordier.processing.mnmd.features;

import processing.core.PApplet;

import com.acordier.processing.mnmd.core.MidiInstrument;
import com.acordier.processing.mnmd.core.MidiReceiver;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Bypass;
import ddf.minim.ugens.Damp;
import ddf.minim.ugens.Delay;
import ddf.minim.ugens.Frequency;
import ddf.minim.ugens.Gain;
import ddf.minim.ugens.MoogFilter;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Summer;
import ddf.minim.ugens.Waves;

/**
 * @author acordier
 *
 */
public class Youki implements MidiInstrument {

	Minim minim;
	AudioOutput out;
	Oscil vco1;
	Oscil vco2;
	Gain vco1Gain, vco2Gain;
	Oscil lfo;
	MoogFilter filter;
	MoogFilter modFilter;
	Summer sum;
	MidiReceiver receiver;
	PApplet sketch;
	AdsrX adsr;
	Summer adsrSum;
	Damp damp;
	Delay delay;
	Gain globalGain;


	/* transpose values */
	int tOsc_1;
	int tOsc_2;
	

	/** default patching */
	public Youki(PApplet sketch) {
		/* manquait l'amplitude du signal */
		this.sketch = sketch;
		/* Max amp, Attack, Decay, Sustain, Release */;
		vco1 = new Oscil(440.F, 0.5F, Waves.SQUARE);
		vco2 = new Oscil(440.F, 0.5F, Waves.SAW);
		tOsc_1 = 0;
		tOsc_2 = -1;
		adsr = new AdsrX(0.5F, 0.00001F, 0.125F, 0.25F, 0.125F);
		filter = new MoogFilter(1200.F, 0.75F);
		modFilter = new MoogFilter(200.F, 0);
		lfo = new Oscil(3.F, 2000.F, Waves.SINE);
		sum = new Summer();
		adsrSum = new Summer();
		minim = new Minim(sketch);
		out = minim.getLineOut();
		lfo.offset.setLastValue(2000.F); // i don't know what i'm doing here
		lfo.patch(filter.frequency);
		//damp = new Damp(0.01F, 0.225F);
		//damp.patch(filter.frequency);
		vco1Gain = new Gain();
		vco1.patch(vco1Gain);
		vco1Gain.patch(sum);
		vco2Gain = new Gain();
		vco2.patch(vco2Gain);
		vco2Gain.patch(sum);
		sum.patch(adsr);
		adsr.patch(adsrSum);
		adsrSum.patch(filter);
		//filter.patch(modFilter);
		delay = new Delay(0.005F, 0.75F, true, true);
		globalGain = new Gain();
		Bypass<Delay> delayBypass = new Bypass<Delay>(delay);
		filter.patch(delayBypass);
		delayBypass.patch(globalGain);
		globalGain.patch(out);
		//delayBypass.activate();
		receiver = new MidiReceiver(this);
		
		
	}

	@Override
	public void noteOn(float dur) {
		//adsr.patch(adsrSum);
		adsr.noteOn();
	}

	@Override
	public void noteOff() {
		adsr.noteOff();
		//adsr.unpatchAfterRelease(adsrSum);
	}

	@Override
	public void noteOn(int note, int velocity) {
		setFrequency(Frequency.ofMidiNote(note));
		setAmplitude(velocity / 254.F);
		noteOn(0.5F);
	}

	@Override
	public void controlChange(int key, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public AudioOutput getAudioOut() {
		return out;
	}

	/**
	 * this wrapper method is responsible of setting the global amplitude value
	 * and applying any transformation induced by local settings
	 */
	public void setAmplitude(float amp) {
		vco1.setAmplitude(amp);
		vco2.setAmplitude(amp);
	}

	/**
	 * this wrapper method is responsible of setting the global frequency value
	 * and applying any transformation induced by local settings
	 */
	public void setFrequency(Frequency frequency) {
		vco1.setFrequency(Frequency.ofMidiNote(frequency.asMidiNote() + tOsc_1 * 12));
		vco2.setFrequency(Frequency.ofMidiNote(frequency.asMidiNote() + tOsc_2 * 12));
	}

	@Override
	public void setAudioOut(AudioOutput out) {
		this.out = out;
	}

	@Override
	public MidiReceiver getReceiver() {
		return receiver;
	}

	/** close resources */
	public void stop() {
		minim.stop();
	}

	public MoogFilter getFilter() {
		return filter;
	}
	
	public Oscil getLFO(){
		return lfo;
	}
	
	public Oscil getVCO1(){
		return vco1;
	}
	
	public Oscil getVCO2(){
		return vco2;
	}

	public AdsrX getAdsr() {
		return adsr;
	}
	
	public Minim getMinim(){
		return minim;
	}

	public Gain getGain() {
		return globalGain;
	}
	
	
	


}
