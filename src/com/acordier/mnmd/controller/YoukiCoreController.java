package com.acordier.mnmd.controller;

import static com.acordier.mnmd.model.YoukiConstants.WAVE_FORMS;

import com.acordier.processing.mnmd.features.Youki;

import ddf.minim.ugens.Waveform;
/**
 * ModelController owns an instance of MidiInstrument and is responsible
 * to execute control request addressed by any individual control controller
 * used by the view.
 * @author acordier
 *
 */
public class YoukiCoreController {
	
	final Youki youki;

	public YoukiCoreController(final Youki youki){
		this.youki = youki;
	}
	
	public void setFrequencyValue(float value){
		youki.getFilter().frequency.setLastValue(value);
	}
	
	
	public float getFrequencyValue(){
		return youki.getFilter().frequency.getLastValue();
	}
	
	public void setResonanceValue(float value){
		youki.getFilter().resonance.setLastValue(value);
	}
	
	public void updateLFO(float value){
		youki.getLFO().offset.setLastValue(1920+value);
	}
	
	public float getResonanceValue(){
		return youki.getFilter().resonance.getLastValue();
	}
	
	public void setAmpValue(float value){
		youki.getAdsr().setAmplitude(value);
	}

	
	public float getAmpValue(){
		return youki.getAdsr().getAmplitude();
	}

	
	public void setAttackValue(float value){
		youki.getAdsr().setAttack(value);
	}

	
	public float getAttackValue(){
		return youki.getAdsr().getAttack();
	}
	
	public void setDecayValue(float value){
		youki.getAdsr().setDecay(value);
	}
	
	public float getDecayValue(){
		return youki.getAdsr().getDecay();
	}
	
	
	public void setSustainValue(float value){
		youki.getAdsr().setSustain(value);
	}
	
	public float getSustainValue(){
		return youki.getAdsr().getSustain();
	}
	
	public void setReleaseValue(float value){
		youki.getAdsr().setRelease(value);
	}
	
	public float getReleaseValue(){
		return youki.getAdsr().getRelease();
	}
	
	public void setGainValue(float value){
		youki.getGain().setValue(value);
	}
	
	public float getGainValue(){
		return youki.getGain().gain.getLastValue();
	}
	
	public void setWaveFormForVCO1(int i){
		youki.getVCO1().setWaveform(WAVE_FORMS[i]);
	}

	public int getWaveFormIndexForVCO1(){
		Waveform waveForm = youki.getVCO1().getWaveform(); 
		for(int i = 0; i < WAVE_FORMS.length; i++){
			if(WAVE_FORMS[i].equals(waveForm)){
				return i;
			}
		}
		return -1;
	}
	
	public void setWaveFormForVCO2(int i){
		youki.getVCO2().setWaveform(WAVE_FORMS[i]);
	}
	
	public int getWaveFormIndexForVCO2(){
		Waveform waveForm = youki.getVCO2().getWaveform(); 
		for(int i = 0; i < WAVE_FORMS.length; i++){
			if(WAVE_FORMS[i].equals(waveForm)){
				return i;
			}
		}
		return -1;
	}
}
