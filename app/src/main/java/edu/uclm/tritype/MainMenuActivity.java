package edu.uclm.tritype;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenuActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}

	public void writing(View view) {
		Intent i=new Intent(this, TriTypeActivity.class);
		startActivity(i);
	}
	
	public void moving(View view) {
		Intent i=new Intent(this, MovingActivity.class);
		startActivity(i);
	}
	
	public void drawing(View view) {
		Intent i=new Intent(this, DrawingActivity.class);
		startActivity(i);
	}
	
	public void byWeb(View view) {
		Intent i=new Intent(this, GetTypeByWeb.class);
		startActivity(i);
	}
}
