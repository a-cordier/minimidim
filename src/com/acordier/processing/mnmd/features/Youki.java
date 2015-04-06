package com.acordier.processing.mnmd.features;

import com.acordier.processing.mnmd.core.MidiInstrument;
import com.acordier.processing.mnmd.core.MidiReceiver;

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
		/* manquait l'amplitude du signal */
		this.sketch = sketch;
		/* Max amp, Attack, Decay, Sustain, Release */
		adsr = new ADSR( 0.4F, 0.05F, 0.8F, 0.03F, 0.0001F );
		osc_1 = new Oscil(440, 0.5F, Waves.SAW);
		osc_2 = new Oscil(440, 0.5F, Waves.SINE);
		filter = new MoogFilter(150, 0.65F);
		lfo = new Oscil(0.250F, 2000.F, Waves.SINE);
		sum = new Summer();
		minim = new Minim(sketch);
		out = minim.getLineOut();
		lfo.offset.setLastValue(2000.F); // i don't know what i'm doing here
		lfo.patch(filter.frequency);
		osc_1.patch(sum);
		osc_2.patch(sum);
		sum.patch(adsr);
		// filter.patch(out);
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
		//adsr.unpatchAfterRelease(out); // patch ends;
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
		osc_1.setAmplitude(amp);
		osc_2.setAmplitude(amp);
	}

	/**
	 * this wrapper method is responsible of setting the global frequency value
	 * and applying any transformation induced by local settings
	 */
	public void setFrequency(Frequency frequency) {
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
