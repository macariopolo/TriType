package edu.uclm.tritype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrawingActivity extends ActionBarActivity {
	private EditText editText01, editText02, editText03, currentEditText;
	private TextView tvResult;
	private Button btnStart1, btnStart2, btnStart3;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);

		this.editText01=(EditText) findViewById(R.id.editTextDrawing1);
		this.editText02=(EditText) findViewById(R.id.editTextDrawing2);
		this.editText03=(EditText) findViewById(R.id.editTextDrawing3);
		this.tvResult=(TextView) findViewById(R.id.textViewDrawingResult);
		this.btnStart1=(Button) findViewById(R.id.buttonDrawingStart1);
		this.btnStart2=(Button) findViewById(R.id.buttonDrawingStart2);
		this.btnStart3=(Button) findViewById(R.id.buttonDrawingStart3);

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
				currentEditText=(EditText) btn.getTag();
				Intent i=new Intent(DrawingActivity.this, DrawingActivity2.class);
				startActivityForResult(i, 100);
			} 
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==100) {
			float length=data.getFloatExtra("length", 0);
			currentEditText.setText("" + (int) length);
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
