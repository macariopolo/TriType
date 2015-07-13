package com.maco.sensorsLibrary.sensors.accelerometer;

import java.lang.reflect.Field;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class AccelerometerListener implements SensorEventListener {
	public static final String HAS_CHANGED = "HasChanged";
	public static final String VALUES = "Values";
	public static final String TIMESTAMP = "Timestamp";

	private static AccelerometerListener yo;
	
	private SensorManager sensorManager;
	private Sensor sensor;
	private AccelerometerHandler handler;
	private float[] lastMeasure;
	private boolean notifyWhenChanges;
	private long interval;
	private long lastTimeStamp;	
	
	private AccelerometerListener(Context ctx, boolean notifyWhenChanges, long interval) {
		if (this.sensor==null) {
			this.sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
			this.sensor=this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
			this.handler=new AccelerometerHandler();
			this.lastMeasure=null;
			this.notifyWhenChanges=notifyWhenChanges;
			this.interval=interval;
		}
	}
	
	public static AccelerometerListener get(Context ctx, boolean notifyWhenChanges, long interval) {
		if (yo==null)
			yo=new AccelerometerListener(ctx, notifyWhenChanges, interval);
		return yo;
	}
	
	public void addReceiver(IAccelerometerReceiver receiver) {
		this.handler.add(receiver);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {		
		if (lastMeasure==null) {
			lastMeasure=event.values;
			lastTimeStamp=event.timestamp;
		}
		long time=event.timestamp-lastTimeStamp;
		if (time<=interval)
			return;
		lastTimeStamp=event.timestamp;
		

		Message msg=this.handler.obtainMessage();
		Bundle b=new Bundle();
		b.putBoolean(HAS_CHANGED, true);
		b.putFloatArray(VALUES, event.values);
		b.putLong(TIMESTAMP, event.timestamp);
		msg.setData(b);
		this.handler.sendMessage(msg);
	}

	public void setNotifyWhenChanges(boolean value) {
		this.notifyWhenChanges=value;
	}

	public void unregisterReceiver(IAccelerometerReceiver receiver) {
		this.handler.remove(receiver);
	}
	
}
