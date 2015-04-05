package com.acordier.processing.mnmd.core;

import ddf.minim.AudioOutput;
import ddf.minim.ugens.Instrument;

public interface MidiInstrument extends Instrument{
	public AudioOutput getAudioOut();
	public void setAudioOut(AudioOutput out);
	public void playNote(float offset, int note, int velocity, float duration);
	public void controlChange(int key, int value);
	public void setAmplitude(float amp);
	public MidiReceiver getReceiver();
}
