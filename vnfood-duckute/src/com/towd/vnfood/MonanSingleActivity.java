package com.towd.vnfood;

import java.io.IOException;

import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.towd.vnfood.datamanager.MyDataBaseManager;
import com.towd.vnfood.utils.Utils;

public class MonanSingleActivity extends Activity {

	private TextView tvName;
	private TextView tvintro;
	private ImageView imvdish;
	private TextView tv_material;
	private TextView tv_howtocook;
	private CheckBox dishfavorite;
	private TextView tvGioiThieu;
	MyDataBaseManager dbManger = new MyDataBaseManager(this);

	MonanObject monan = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

		setContentView(R.layout.detail_monan);

		tvName = (TextView) findViewById(R.id.txt_dishname);
		imvdish = (ImageView) findViewById(R.id.dishpic);
		dishfavorite = (CheckBox) findViewById(R.id.dishfavorite);
		tv_material = (TextView) findViewById(R.id.txt_dishmaterial);
		tv_howtocook = (TextView) findViewById(R.id.txt_howtocook);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		
		try {
			dbManger.createDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		monan = (MonanObject) bundle
				.getSerializable(MonanListActivity.INTENT_KEY);
		
		// set cac thuoc tinh cua mon an
		
		if(monan.getFavorite() == 1){
			dishfavorite.setChecked(true);
		}else{
			dishfavorite.setChecked(false);
		}
		
	
		dishfavorite.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					monan.setFavorite(1);
					if(dbManger.updateFavorite(monan.getId(), 1)>0)
						Toast.makeText(MonanSingleActivity.this, "Đã đánh dấu!", Toast.LENGTH_SHORT).show();
					}
					else{
						monan.setFavorite(0);
						if(dbManger.updateFavorite(monan.getId(), 0)>0)
							Toast.makeText(MonanSingleActivity.this, "Bỏ đánh dấu!", Toast.LENGTH_SHORT).show();
						}
				}
		});
		
		
		tvName.setText(monan.getDishname().toString() + " \n");
		if (monan.getIntro() != null) {
			tvGioiThieu = (TextView) findViewById(R.id.tvGioiThieu);
			tvGioiThieu.setText("Giới thiệu: " );
			tvintro = (TextView) findViewById(R.id.txt_intro);
			tvintro.setText(monan.getIntro().toString() + " \n");
		}// ko setext
		if (monan.getMaterial() != null)
			tv_material.setText(monan.getMaterial().toString() + " \n");
		if (monan.getHowtocook() != null)
			tv_howtocook.setText(monan.getHowtocook() + " \n");

		// thêm image thì dùng đoạn này set nhé

		Utils utils = new Utils(this);
		String dishpc = monan.getDishpics();
		String link = "DoUong/" + dishpc;
		;
		if (utils.assetExists("DoUong/" + dishpc)) {

			link = "DoUong/" + dishpc;

		} else if (utils.assetExists("MonAnThai/" + dishpc)) {
			link = "MonAnThai/" + dishpc;

		} else if (utils.assetExists("MonTrung/" + dishpc)) {
			link = "MonTrung/" + dishpc;

		} else if (utils.assetExists("MonBac/" + dishpc)) {
			link = "MonBac/" + dishpc;

		} else if (utils.assetExists("MonBanh/" + dishpc)) {
			link = "MonBanh/" + dishpc;

		} else if (utils.assetExists("MonChay/" + dishpc)) {
			link = "MonChay/" + dishpc;

		} else if (utils.assetExists("MonChe/" + dishpc)) {
			link = "MonChe/" + dishpc;

		} else if (utils.assetExists("MonNam/" + dishpc)) {
			link = "MonNam/" + dishpc;

		} else if (utils.assetExists("MonNuocNgoai/" + dishpc)) {
			link = "MonNuocNgoai/" + dishpc;

		}

		imvdish.setImageBitmap(utils.getBitmapFromAssets(link));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
		
	}
}
