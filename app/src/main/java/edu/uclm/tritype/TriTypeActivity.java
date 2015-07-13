package edu.uclm.tritype;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TriTypeActivity extends ActionBarActivity {
	private EditText editText01, editText02, editText03;
	private TextView tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tri_type);
		this.editText01=(EditText) findViewById(R.id.editTextMoving1);
		this.editText02=(EditText) findViewById(R.id.editText2);
		this.editText03=(EditText) findViewById(R.id.editText3);
		this.tvResult=(TextView) findViewById(R.id.textViewResult);
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
