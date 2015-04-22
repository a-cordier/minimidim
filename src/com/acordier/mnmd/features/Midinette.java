package com.acordier.mnmd.features;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import com.acordier.mnmd.core.AbstractMidiSequencer;
import com.acordier.mnmd.core.MidiInstrument;
import com.acordier.mnmd.model.Step;
import com.acordier.mnmd.model.StepSequence;

public class Midinette extends AbstractMidiSequencer {

	private StepSequence stepSequence;
	private int ticksPerStep;

	public Midinette(MidiInstrument instrument) {
		initSequencer(instrument);
		setTempo(120);
		setSignature(4, 4); // initialize anything being related to timing
	}

	public Midinette() {
		stepSequence = new StepSequence();
		setSignature(4, 4);
		setTempo(120);
		stop();
	}

	public void randomize(int octave) {
		stepSequence.randomize(octave);
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
				addNote(step.getNote(), step.getVelocity(), i * ticksPerStep,
						ticksPerStep*2);
			}
			i++;
		}
	
	}

	@Override
	public void setSignature(int stepsPerBeat, int beats) {
		ticksPerStep = sequence.getResolution() / stepsPerBeat;
		instrument.getAudioOut().setDurationFactor(1.F);
		int stepCount = stepsPerBeat * beats; // setting the size of the step
												// sequence
		if (stepSequence == null) {
			stepSequence = new StepSequence(stepCount);
		}
		while (stepCount < stepSequence.size()) {
			stepSequence.shrink();
		}
		while (stepCount > stepSequence.size()) {
			stepSequence.grow();
		}
		try {
			/*
			 * we set the end of track meta message for proper looping (status
			 * code for EOT is 47=0x2f)
			 */
			MetaMessage enOfTrackMsg = new MetaMessage();
			enOfTrackMsg.setMessage(0x2F, new byte[] {}, 0);
			MidiEvent enOfTrackEvt = new MidiEvent(enOfTrackMsg,
					stepSequence.size() * ticksPerStep);
			track.add(enOfTrackEvt);
		} catch (InvalidMidiDataException e) {
			System.out
					.println("Error setting end of track event (invalid midi data)");
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

	public StepSequence getStepSequence() {
		return stepSequence;
	}

	public void setStepSequence(StepSequence stepSequence) {
		this.stepSequence = stepSequence;
	}
	


}
