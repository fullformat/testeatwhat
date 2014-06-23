package com.towd.vnfood.sildemenu;

import com.towd.vnfood.R;
import com.towd.vnfood.R.layout;
import com.towd.vnfood.R.menu;
import com.towd.vnfood.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.TextView;

public class DieuCanBiet extends Activity {
	Utils utils = new Utils(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dieu_can_biet);
		TextView tv = (TextView) findViewById(R.id.tvNoiDungCanBiet);
		TextView tv2 = (TextView) findViewById(R.id.noiDungdieuCanBiet2);
		
			String text = null;
			String filename = "dieucanbiet_1.txt";
			text= utils.readFromFile(filename);
			if(text!= null){
			tv.setText(text);
			}
			
			String text2 = null;
			String filename2 = "dieucanbiet_2.txt";
			text2= utils.readFromFile(filename2);
			if(text2!= null){
			tv2.setText("\n"+text2);
			}
			
	}

	
}
