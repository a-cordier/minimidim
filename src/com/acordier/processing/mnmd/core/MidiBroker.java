package com.acordier.processing.mnmd.core;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice.Info;

/**
 * MidiBroker provides static methods to access
 * and retreive midi hardware devices
 * @author acordier
 *
 */
public class MidiBroker {
	
	public static MidiDevice getMidiOutputDevice(String name) {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		MidiDevice device = null;
		for (Info info : infos) {
			if (info.getName().equals(name)) {
				try {
					device = MidiSystem.getMidiDevice(info);
					if(device.getMaxReceivers()!=0){
						return device;
					}
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static MidiDevice getMidiInputDevice(String name) {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		MidiDevice device = null;
		for (Info info : infos) {
			if (info.getName().equals(name)) {
				try {
					device = MidiSystem.getMidiDevice(info);
					if(device.getMaxTransmitters()!=0){
						return device;
					}
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static MidiDevice[] getMidiInputDevices() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		List<MidiDevice> devices = new ArrayList<MidiDevice>(0);
		MidiDevice device;
		for (Info info : infos) {			
				try {
					device = MidiSystem.getMidiDevice(info);
					if(device.getMaxTransmitters()!=0){
						devices.add(device);
					}
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			
		}
		return (MidiDevice[])devices.toArray();
	}
	
	public static MidiDevice[] getMidiOutputDevices() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		List<MidiDevice> devices = new ArrayList<MidiDevice>(0);
		MidiDevice device;
		for (Info info : infos) {			
				try {
					device = MidiSystem.getMidiDevice(info);
					if(device.getMaxReceivers()!=0){
						devices.add(device);
					}
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			
		}
		return (MidiDevice[])devices.toArray();
	}
}
