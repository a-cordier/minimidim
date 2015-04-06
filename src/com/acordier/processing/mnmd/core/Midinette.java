package com.acordier.processing.mnmd.core;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.acordier.processing.mnmd.model.Step;
import com.acordier.processing.mnmd.model.StepSequence;

public class Midinette extends AbstractMidiSequencer {

	private StepSequence stepSequence;

	public Midinette(MidiInstrument instrument) {
		try {
			this.instrument = instrument;
			sequencer = MidiSystem.getSequencer();
			sequencer.getTransmitter().setReceiver(instrument.getReceiver());
			stepSequence = new StepSequence();
			System.out.println(stepSequence.size());
			setSignature(4, 4); // initialize anything being related to timing
								// and sequencing
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
		addNotes();
	}

	public void fourBeat(int note) {
		stepSequence.fourBeat(note);
		addNotes();

	}

	private void addNotes() {
		int i = 0;
		for (Step step : stepSequence) {
			if (step.isEnabled()) {
				addNote(step.getNote(), step.getVelocity(), i, 1);
			} else
				i++;
		}
	}

	@Override
	public void setSignature(int ticksPerBeat, int beats) {
		try {
			this.ticksPerBeat = ticksPerBeat;
			sequence = new Sequence(Sequence.PPQ, ticksPerBeat);
			track = sequence.createTrack();
			sequencer.setSequence(sequence);
			int stepCount = ticksPerBeat * beats;
			if (stepSequence == null) {
				stepSequence = new StepSequence(stepCount);
			}
			while (stepCount < stepSequence.size()) {
				stepSequence.shrink();
			}
			while (stepCount > stepSequence.size()) {
				stepSequence.grow();
			}
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void play(boolean loop) {
		this.loop = loop;
		try {
			if (!sequencer.isOpen()) {
				sequencer.open();
			}
			if (sequencer != null && sequence != null && sequencer.isOpen()) {
				if (loop) {
					sequencer.setLoopStartPoint(0);
					sequencer.setLoopEndPoint(-1);
					sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
				}
				sequencer.start();
			}

		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}

	}


	@Override
	public void stop() {
		sequencer.stop();
		sequencer.setMicrosecondPosition(0);
	}

}
