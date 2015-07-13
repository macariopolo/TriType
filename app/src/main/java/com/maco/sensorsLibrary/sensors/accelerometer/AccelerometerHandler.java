package com.maco.sensorsLibrary.sensors.accelerometer;

import java.util.Vector;

import android.os.Handler;
import android.os.Message;

class AccelerometerHandler extends Handler {
	private Vector<IAccelerometerReceiver> receivers;

	public AccelerometerHandler() {
		this.receivers=new Vector<IAccelerometerReceiver>();
	}
	
	public void add(IAccelerometerReceiver receiver) {
		receivers.add(receiver);
	}
	
	@Override
	public void handleMessage(Message msg) {
		for (IAccelerometerReceiver receiver : receivers) {
			receiver.setAccelerometerValues(msg);
		}
	}

	public void remove(IAccelerometerReceiver receiver) {
		receivers.remove(receiver);
	}
}
