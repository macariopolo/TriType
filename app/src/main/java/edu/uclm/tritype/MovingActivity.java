package edu.uclm.tritype;

import com.maco.sensorsLibrary.sensors.SensorsSwitchBoard;
import com.maco.sensorsLibrary.sensors.accelerometer.AccelerometerListener;
import com.maco.sensorsLibrary.sensors.accelerometer.IAccelerometerReceiver;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MovingActivity extends ActionBarActivity implements IAccelerometerReceiver {
	private EditText editText01, editText02, editText03, currentEditText;
	private TextView tvResult;
	private Button btnStart1, btnStart2, btnStart3;
	private SeekBar seekBar;
	private int currentMeasure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moving);

		this.seekBar=(SeekBar) findViewById(R.id.seekBarMovement);
		this.editText01=(EditText) findViewById(R.id.editTextMoving1);
		this.editText02=(EditText) findViewById(R.id.editTextMoving2);
		this.editText03=(EditText) findViewById(R.id.editTextMoving3);
		this.tvResult=(TextView) findViewById(R.id.textViewInfo);
		this.btnStart1=(Button) findViewById(R.id.buttonMovingStart1);
		this.btnStart2=(Button) findViewById(R.id.buttonMovingStart2);
		this.btnStart3=(Button) findViewById(R.id.buttonMovingStart3);

		this.btnStart1.setTag(this.editText01);
		this.btnStart2.setTag(this.editText02);
		this.btnStart3.setTag(this.editText03);
		this.btnStart1.setOnClickListener(new StartListener(btnStart1));
		this.btnStart2.setOnClickListener(new StartListener(btnStart2));
		this.btnStart3.setOnClickListener(new StartListener(btnStart3));
	}

	public class StartListener implements OnClickListener {
		private Button btn;

		public StartListener(Button btn) {
			this.btn=btn;
		}

		@Override
		public void onClick(View v) {
			if (this.btn.getText().equals(getResources().getText(R.string.start))) {
				this.btn.setText(getResources().getText(R.string.stop));
				currentEditText=(EditText) btn.getTag();
				receiveNotifications(true);
			} else {
				this.btn.setText(getResources().getText(R.string.start));
				receiveNotifications(false);
			}
		}
	}

	private void receiveNotifications(boolean value) {
		SensorsSwitchBoard ssb=SensorsSwitchBoard.get(MovingActivity.this);
		if (value)
			ssb.registerAccelerometerReceiver(MovingActivity.this, true);
		else
			ssb.unregisterAccelerometerReceiver(this);
	}

	@Override
	public void setAccelerometerValues(Message msg) {
		Bundle b=msg.getData();
		float[] values = b.getFloatArray(AccelerometerListener.VALUES);
		float value=(float) (Math.pow(values[0], 1)+Math.pow(values[1], 1) + Math.pow(values[2], 1));
		try {
			this.currentMeasure=50+(int) value;
			this.seekBar.setProgress(currentMeasure);
			this.currentEditText.setText("" + currentMeasure);
		} catch (Exception e) {
			Log.e("", e.getMessage(), e);
		}
	}

	public void getType(View view) {
		TriType t=new TriType();
		try {
			int a=getValue(this.editText01),
				b=getValue(this.editText02),
				c=getValue(this.editText03);
			t.setI(a);
			t.setJ(b);
			t.setK(c);
			String result=t.getType();
			if (TriType.EQUILATERAL.equals(result))
				this.tvResult.setText(this.getResources().getString(R.string.equilateral));
			else if (TriType.ISOSCELES.equals(result))
				this.tvResult.setText(this.getResources().getString(R.string.isosceles));
			else if (TriType.SCALENE.equals(result))
				this.tvResult.setText(this.getResources().getString(R.string.scalene));
			else 
				this.tvResult.setText(this.getResources().getString(R.string.not_a_triangle));
		}
		catch (NumberFormatException e) {
			tvResult.setText(this.getResources().getString(R.string.number_expected));
		}
	}

	private int getValue(EditText et) {
		return Integer.parseInt(et.getText().toString());			
	}
}
