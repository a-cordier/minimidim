# MINIMIDIM

Builting a midi synth scripting environment based on the processing minim library

## Components

### Sequencer

Sequencer should be responsible for setting a tempo, playing a sequence, and send events to instruments or effects

### MidiInstrument

This interface extends the Instrument interface, adding methods enable tying of instruments to classes extending AbstractMidiSequencer

