package com.acordier.processing.mnmd.core;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

import com.acordier.processing.mnmd.model.Step;
import com.acordier.processing.mnmd.model.StepSequence;

public class Midinette extends AbstractMidiSequencer implements MetaEventListener {

	private StepSequence stepSequence;
	private static final int END_OF_TRACK_EVENT = 0x2F;

	public Midinette(MidiInstrument instrument) {
		try {
			this.instrument = instrument;
			sequencer = MidiSystem.getSequencer();
			sequencer.getTransmitter().setReceiver(instrument.getReceiver());
			stepSequence = new StepSequence();
			setSignature(4, 4); // initialize anything being related to timing and sequencing
			setTempo(120);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

	public Midinette() {
		stepSequence = new StepSequence();
		setSignature(4, 4);
		setTempo(120);
		stop();
	}

	public void randomize() {
		stepSequence.randomize();
		int i = 0;
		for(Step step: stepSequence){
			if(step.isEnabled()){
				addNote(step.getNote(), step.getVelocity(), i, 4);
				System.out.println(step);
			} else System.out.println("no beat");
			i++;
		}
	}

	public void fourBeat(int note) {
		stepSequence.fourBeat(note);
	}


	@Override
	public void setSignature(int ticksPerBeat, int beats) {
		try {
			this.ticksPerBeat = ticksPerBeat;
			sequence = new Sequence(Sequence.PPQ, ticksPerBeat);
			track = sequence.createTrack();
			sequencer.setSequence(sequence);
			instrument.getAudioOut().setDurationFactor(1.F / ticksPerBeat); // default note duration
			int stepCount = ticksPerBeat * beats;
			if (stepSequence == null) {
				stepSequence = new StepSequence(stepCount);
			}
			while (stepCount < stepSequence.getStepCount()) {
				stepSequence.shrink();
			}
			while (stepCount > stepSequence.getStepCount()) {
				stepSequence.grow();
			}
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void play(boolean loop) {
		try {
			this.loop = loop;
			if(!sequencer.isOpen()){
				sequencer.open();
			}
			sequencer.start();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
		sequencer.stop();
		sequencer.setMicrosecondPosition(0);
	}

	@Override // Handling looping through the sequence
	public void meta(MetaMessage message) {
		 if (message.getType() == END_OF_TRACK_EVENT) {
		      if (sequencer != null && sequencer.isOpen() && loop) {
		        sequencer.start();
		      }
		    }
	}	 

}
