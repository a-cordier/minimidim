package com.acordier.processing.mnmd.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;

public class StepSequence implements Iterable<Step>{

	private List<Step> steps;
	private int stepCount;
	private Random random;

	public StepSequence(int stepCount) {
		this.stepCount = stepCount;
		steps = new ArrayList<Step>(stepCount);
		feed();
	}

	public StepSequence() {
		stepCount = 16;
		steps = new ArrayList<Step>(stepCount);
		feed();
	}
	
	private void feed(){
		for (byte i = 0; i < stepCount; i++) {
			steps.add(new Step());
		}
	}

	/**
	 * Reset all beats
	 */
	public void reset() {
		for (byte i = 0; i < stepCount; i++)  {
			steps.get(i).reset();
		}
	}

	/**
	 * Feed with random beats
	 */
	public void randomize() {
		random = new Random();
		int idx;
		for (byte i = 0; i < stepCount; i++)  {
			idx = PApplet.ceil(random.nextInt(6));
			steps.set(i, new Step(127 - random.nextInt(127 - 50),
					Math.random() > 0.5 ? true : false));
			steps.get(i).setNote(ScalesConstants.pNotes[0][idx]);
		}
	}
	
	/**
	 * Feed with random beats
	 */
	public void fourBeat(int note) {
		for (byte i = 0; i < stepCount; i++)  {
			steps.set(i, new Step(105,
					i%4==0 ? true : false));
			steps.get(i).setNote(note);
		}
	}
	
	/**
	 * Add one beat
	 */
	public void grow(){ 
		this.steps.add(new Step());
	}
	
	/**
	 * Remove one beat
	 */
	public void shrink(){
		this.steps.remove(this.steps.size()-1);
	}

	@Override
	public Iterator<Step> iterator() {
		return steps.iterator();
	}

	public int getStepCount() {
		return stepCount;
	}
	
	
	
	

}
