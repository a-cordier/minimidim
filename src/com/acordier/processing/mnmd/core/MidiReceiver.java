package com.acordier.processing.mnmd.core;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MidiReceiver implements Receiver {

	private MidiInstrument instrument;

	public MidiReceiver(MidiInstrument instrument) {
		this.instrument = instrument;
	}

	@Override
	public void send(MidiMessage message, long timestamp) {
		if (message instanceof ShortMessage) {
			handleShortMessage((ShortMessage) message, timestamp);
		}
	}

	@Override
	public void close() {
	}

	private void handleShortMessage(ShortMessage message, long timestamp) {
		if (message.getCommand() == ShortMessage.NOTE_ON) {
			int note = message.getData1(), velocity = message.getData2();
			instrument.noteOn(note, velocity);
		}
		if (message.getCommand() == ShortMessage.NOTE_OFF) {
			instrument.noteOff();
		}
	}

}
