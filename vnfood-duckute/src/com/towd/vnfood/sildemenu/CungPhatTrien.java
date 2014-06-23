package com.towd.vnfood.sildemenu;

import com.towd.vnfood.R;
import com.towd.vnfood.R.layout;
import com.towd.vnfood.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CungPhatTrien extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cung_phat_trien);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cung_phat_trien, menu);
		return true;
	}

}
