package edu.uclm.tritype;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.tritype.http.Proxy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GetTypeByWeb extends ActionBarActivity {
	private EditText editText01, editText02, editText03;
	private TextView tv1, tvInfo, tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tri_type);
		this.editText01=(EditText) findViewById(R.id.editTextMoving1);
		this.editText02=(EditText) findViewById(R.id.editText2);
		this.editText03=(EditText) findViewById(R.id.editText3);
		this.tv1=(TextView) findViewById(R.id.textView1);
		this.tv1.setText(getResources().getString(R.string.title_activity_get_type_by_web));
		this.tvInfo=(TextView) findViewById(R.id.textViewInfo);
		tvInfo.setText(getResources().getString(R.string.instructions_web));
		this.tvResult=(TextView) findViewById(R.id.textViewResult);
	}
	
	public void getType(View view) {
		try {
			int a=getValue(this.editText01),
				b=getValue(this.editText02),
				c=getValue(this.editText03);
			JSONObject jso=new JSONObject();
			jso.put("i", a);
			jso.put("j", b);
			jso.put("k", c);

			Proxy p=Proxy.get();
			JSONObject resp = p.postJSONOrderWithResponse("getType", jso);
			String result=resp.getString("result");
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
		} catch (JSONException e) {
			tvResult.setText(e.getMessage());
		} catch (InterruptedException e) {
			tvResult.setText(e.getMessage());
		} catch (ExecutionException e) {
			tvResult.setText(e.getMessage());
		}
	}

	private int getValue(EditText et) {
		return Integer.parseInt(et.getText().toString());			
	}
}
