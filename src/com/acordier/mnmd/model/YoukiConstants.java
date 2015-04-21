package com.acordier.mnmd.model;

import ddf.minim.ugens.Waveform;
import ddf.minim.ugens.Waves;

public class YoukiConstants {
	public final static float FREQ_MIN = 50.F; // hz
	public final static float FREQ_MAX = 2200.F;
	public final static float RES_MIN = 0.F;
	public final static float RES_MAX = 0.85F;
	public final static float GAIN_MIN = -60.F; // db
	public final static float GAIN_MAX = 2.F;
	public final static float AMP_MIN = 0.F;
	public final static float AMP_MAX = 1.F;
	public final static float ATT_MIN = 0.00001F;
	public final static float ATT_MAX = 0.25F;
	public final static float DEC_MIN = 0.001F;
	public final static float DEC_MAX = 0.25F;
	public final static float SUS_MIN = 0.F;
	public final static float SUS_MAX = 0.25F;
	public final static float REL_MIN = 0.F; // sec
	public final static float REL_MAX = 1.F;
	public final static Waveform[] WAVE_FORMS = {
		Waves.SINE,
		Waves.TRIANGLE,
		Waves.SAW,
		Waves.SQUARE,
		Waves.QUARTERPULSE
	};
}
