package com.acordier.processing.mndmd.core;

import processing.core.PApplet;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.ADSR;
import ddf.minim.ugens.Frequency;
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
	Oscil osc_1;
	Oscil osc_2;
	Oscil lfo;
	MoogFilter filter;
	Summer sum;
	ADSR adsr;
	MidiReceiver receiver;
	PApplet sketch;
	
	
	/** default patching */
	public Youki(PApplet sketch) {
		/* manquait l'amplitude du signal*/
		this.sketch = sketch;
		/* Max amp, Attack, Decay, Sustain, Release */
		adsr = new ADSR( 0.5F, 0.005F, 0.1F, 0.03F, 0.01F );
		osc_1 = new Oscil( 440, 0.5F, Waves.SAW  ); 
		osc_2 = new Oscil( 440, 0.5F, Waves.SAW );
		filter = new MoogFilter(200, 0.75F);
		lfo = new Oscil( 0.125F, 1500.F, Waves.SINE );
		// centered on 500 Hz
	    lfo.offset.setLastValue( 2000.F ); // i don't know what i'm doing here
		lfo.patch(filter.frequency);
		sum = new Summer();
		minim = new Minim(sketch);
		out = minim.getLineOut();
		osc_1.patch(sum);
		osc_2.patch(sum);
		sum.patch(filter);
		filter.patch(adsr);
		receiver = new MidiReceiver(this);
	}
	
	@Override 
	public void noteOn(float dur) {
		adsr.patch(out);
		adsr.noteOn(); // env begins

	}

	@Override
	public void noteOff() {
		adsr.noteOff(); // env release begins
		adsr.unpatchAfterRelease(out); // patch ends;
	}
	
	@Override
	public void playNote(float offset, int note, int velocity, float duration){
		setFrequency(Frequency.ofMidiNote(note));
		setAmplitude(velocity/254.F);
		out.playNote(offset, duration, this);
	}
	
	@Override
	public void controlChange(int key, int value) {
		// TODO Auto-generated method stub
		
	}
		
	@Override
	public AudioOutput getAudioOut() {
		return out;
	} 
	
	/** this wrapper method is responsible of setting 
    the global amplitude value and applying any 
    transformation induced by local settings */
	public void setAmplitude(float amp){
		osc_1.setAmplitude(amp);
		osc_2.setAmplitude(amp);
	}
	
	/** this wrapper method is responsible of setting 
	    the global frequency value and applying any 
	    transformation induced by local settings */
	public void setFrequency(Frequency frequency){
		osc_1.setFrequency(frequency);
		osc_2.setFrequency(frequency);
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

	public void setFilter(MoogFilter filter) {
		this.filter = filter;
	}

	public ADSR getAdsr() {
		return adsr;
	}

	public void setAdsr(ADSR adsr) {
		this.adsr = adsr;
	}

	

	
	
}
