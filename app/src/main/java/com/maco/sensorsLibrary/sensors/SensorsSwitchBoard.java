package com.maco.sensorsLibrary.sensors;

import com.maco.sensorsLibrary.sensors.accelerometer.AccelerometerListener;
import com.maco.sensorsLibrary.sensors.accelerometer.IAccelerometerReceiver;

import android.content.Context;

public class SensorsSwitchBoard {
	private static SensorsSwitchBoard yo;
	
	private Context context;
	private AccelerometerListener accelerometerListener;
	
	public SensorsSwitchBoard(Context applicationContext) {
		context=applicationContext;
	}

	public static SensorsSwitchBoard get(Context applicationContext) {
		if (yo==null)
			yo=new SensorsSwitchBoard(applicationContext);
		return yo;
	}

	public void registerAccelerometerReceiver(IAccelerometerReceiver receiver, boolean notifyWhenChanges) {
		accelerometerListener=AccelerometerListener.get(context, notifyWhenChanges, 5000000);
		accelerometerListener.addReceiver(receiver);
	}

	public void setNotifyAccelerometerWhenChanges(boolean value) {
		accelerometerListener.setNotifyWhenChanges(value);
	}
	
	public void unregisterAccelerometerReceiver(IAccelerometerReceiver receiver) {
		accelerometerListener.unregisterReceiver(receiver);
	}
}
