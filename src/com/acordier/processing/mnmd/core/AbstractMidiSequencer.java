package com.acordier.processing.mnmd.core;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public abstract class AbstractMidiSequencer {

	protected Sequence sequence;
	protected Sequencer sequencer;
	protected Track track;;
	protected int tempo; // tempo expressed in bpm
	protected boolean loop;
	protected MidiInstrument instrument;

	public void initSequencer(MidiInstrument instrument) {
		this.instrument = instrument;
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.getTransmitter().setReceiver(instrument.getReceiver());
			sequence = new Sequence(Sequence.PPQ, 24);
			sequencer.setSequence(sequence);
			track = sequence.createTrack();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	public void initSequencer(MidiInstrument instrument, int resolution) {
		this.instrument = instrument;
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.getTransmitter().setReceiver(instrument.getReceiver());
			sequence = new Sequence(Sequence.PPQ, resolution);
			sequencer.setSequence(sequence);
			track = sequence.createTrack();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	public void setTempo(int tempo) {
		instrument.getAudioOut().setTempo(tempo);
		sequencer.setTempoInBPM(tempo);
		this.tempo = tempo;
	}

	/**
	 * @return the tempo, expressed in beats per minute
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * 
	 * @param eventType
	 * @param note
	 * @param tick
	 */
	private void addMidiEvent(int eventType, int note, int velocity, int tick)
			throws InvalidMidiDataException {
		ShortMessage message = new ShortMessage();
		message.setMessage(eventType, note, velocity);
		MidiEvent event = new MidiEvent(message, tick);
		track.add(event);
	}

	/**
	 * 
	 * @param note
	 *            : the pitch value for the note to be played
	 * @param velocity
	 *            : the velocity value for the note to be played
	 * @param position
	 *            : the position for this note, expressed in ticks
	 * @param duration
	 *            : the duration for this note, expressed in ticks
	 * @post schedule the note to be played starting at position for the given
	 *       duration
	 */
	public void addNote(int note, int velocity, int position, int duration) {
		try {
			addMidiEvent(ShortMessage.NOTE_ON, note, velocity, position);
			addMidiEvent(ShortMessage.NOTE_OFF, note, velocity, position
					+ duration);
		} catch (InvalidMidiDataException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param an
	 *            instance of any class implementing th MidiInstrument interface
	 * @post links sequencer to an instance a midi instrument
	 **/
	public void setSynth(MidiInstrument instrument) {
		this.instrument = instrument;
		instrument.getAudioOut().setTempo(tempo);
	}

	/**
	 * @param ticks
	 *            : the number of ticks per beat, giving the value of the note
	 * @param beats
	 *            : the number of beats per measure - handled by the concrete
	 *            class
	 * @post set time signature for the concrete sequencer
	 */
	public abstract void setSignature(int ticks, int beats);

	public abstract void play(boolean loop);

	public abstract void stop();

}
