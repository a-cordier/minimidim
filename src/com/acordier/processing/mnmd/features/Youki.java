package com.acordier.processing.mnmd.features;

import processing.core.PApplet;

import com.acordier.processing.mnmd.core.MidiInstrument;
import com.acordier.processing.mnmd.core.MidiReceiver;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
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
	Oscil vco_1;
	Oscil vco_2;
	Oscil lfo;
	MoogFilter filter;
	Summer sum;
	MidiReceiver receiver;
	PApplet sketch;
	
	/* transpose values */
	int tOsc_1;
	int tOsc_2;
	

	/** default patching */
	public Youki(PApplet sketch) {
		/* manquait l'amplitude du signal */
		this.sketch = sketch;
		/* Max amp, Attack, Decay, Sustain, Release */;
		vco_1 = new Oscil(440.F, 0.5F, Waves.SQUARE);
		vco_2 = new Oscil(440.F, 0.5F, Waves.SAW);
		tOsc_1 = 0;
		tOsc_2 = 1;
		filter = new MoogFilter(250.F, 0.75F);
		lfo = new Oscil(0.03125F, 2000.F, Waves.SINE);
		sum = new Summer();
		minim = new Minim(sketch);
		out = minim.getLineOut();
		lfo.offset.setLastValue(2000.F); // i don't know what i'm doing here
		//lfo.patch(filter.frequency);
		vco_1.patch(sum);
		vco_2.patch(sum);
		sum.patch(filter);
		receiver = new MidiReceiver(this);
	}

	@Override
	public void noteOn(float dur) {
		filter.patch(out);
	}

	@Override
	public void noteOff() {
		filter.unpatch(out);
	}

	@Override
	public void noteOn(int note, int velocity) {
		setFrequency(Frequency.ofMidiNote(note));
		setAmplitude(velocity / 254.F);
		noteOn(0);
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
		vco_1.setAmplitude(amp);
		vco_2.setAmplitude(amp);
	}

	/**
	 * this wrapper method is responsible of setting the global frequency value
	 * and applying any transformation induced by local settings
	 */
	public void setFrequency(Frequency frequency) {
		vco_1.setFrequency(Frequency.ofMidiNote(frequency.asMidiNote() + tOsc_1 * 12));
		vco_2.setFrequency(Frequency.ofMidiNote(frequency.asMidiNote() + tOsc_2 * 12));
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

}
