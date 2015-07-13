package com.maco.sensorsLibrary.actuators;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.maco.sensorsLibrary.sensors.SensorsSwitchBoard;

public class AlarmPlayer extends Thread {
	
	private Context context;
	private AudioManager audioManager;
	private SoundPool soundPool;
	private boolean playing;
    private int streamID;
    private int voice;
    private float maxVolume;
	
	public AlarmPlayer(Context ctx, int resID) {
		this.context=ctx;
		audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		voice=soundPool.load(this.context, resID, 2);
	}
	
	public void run() {
	   	playing=true;
	   	do {
			streamID=soundPool.play(voice, maxVolume, maxVolume, 10, 0, 1f);
	    	SensorsSwitchBoard ssb=SensorsSwitchBoard.get(context);
	    	ssb.setNotifyAccelerometerWhenChanges(true);
	    	try {
				Thread.sleep(7000);
			} catch (InterruptedException e){}
	   	} while (this.playing);
	}

	public boolean isPlaying() {
		return this.playing;
	}

	public void stopPlaying() {
		soundPool.stop(streamID);
		playing=false;
	}
}
