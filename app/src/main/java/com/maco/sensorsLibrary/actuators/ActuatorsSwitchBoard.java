package com.maco.sensorsLibrary.actuators;

import java.util.Calendar;
import java.util.Vector;

import android.content.Context;
import android.os.Message;

import com.maco.sensorsLibrary.sensors.accelerometer.AccelerometerListener;
import com.maco.sensorsLibrary.sensors.accelerometer.IAccelerometerReceiver;

public class ActuatorsSwitchBoard implements IAccelerometerReceiver {
	private static ActuatorsSwitchBoard yo;
	private AlarmPlayer alarmPlayer;
	private Context context;
	
	private ActuatorsSwitchBoard(Context ctx) {
		this.context=ctx;
	}
	
	public static ActuatorsSwitchBoard get(Context ctx) {
		if (yo==null)
			yo=new ActuatorsSwitchBoard(ctx);
		return yo;
	}
	
	@Override
	public void setAccelerometerValues(Message msg) {
		boolean hasChanged= msg.getData().getBoolean(AccelerometerListener.HAS_CHANGED);
		/*if (hasChanged && alarmPlayer.isPlaying()) {
			alarmPlayer.stopPlaying();
			this.alarmPlayer=new AlarmPlayer(this.context, R.raw.ring);
		} else if (!alarmPlayer.isPlaying()) {
			long eventTime=msg.getData().getLong(AccelerometerListener.TIMESTAMP);
			if (isNotDisturbingTime(eventTime))
				return;
			alarmPlayer.start();
		}*/
	}

}
