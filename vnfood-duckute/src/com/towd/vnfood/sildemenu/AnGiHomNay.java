package com.towd.vnfood.sildemenu;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.towd.vnfood.MonanObject;
import com.towd.vnfood.MonanSingleActivity;
import com.towd.vnfood.R;
import com.towd.vnfood.datamanager.MyDataBaseManager;
import com.towd.vnfood.utils.Constants;
import com.towd.vnfood.utils.Utils;

public class AnGiHomNay extends Activity {
	
	MyDataBaseManager db;
	int dishid = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_an_gi_hom_nay);
		int[] randomArr = new int[244];
		for (int i = 0; i < 244; i++) {
			randomArr[i] = i;
		}
		Random a = new Random();
		dishid = a.nextInt(randomArr.length);
		
		db = new MyDataBaseManager(this);
		try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Lỗi tạo cơ sỡ dữ liệu", Toast.LENGTH_SHORT)
					.show();
		}
		
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
		
		MonanObject mon = loadMonAn();
		
		Intent intent = new Intent(AnGiHomNay.this, MonanSingleActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.INTENT_KEY, mon);
		intent.putExtras(bundle);
		Toast.makeText(this, "úm ba la xì bùa \n:))", Toast.LENGTH_LONG).show();
		startActivity(intent);
		finish();
		
	}
	public MonanObject loadMonAn() {
		MonanObject toDayMonAn = new MonanObject();
		int favorite = 0;
		
		// dựa vào category để lấy ra danh sách các món ăn thuộc category đó , dùng sqlite
		Cursor cu = db.getTodayDish(dishid);
		if(cu != null){
			while(cu.moveToNext()) {
				MonanObject monan = new MonanObject();
				int id = cu.getInt(cu.getColumnIndex("dishid"));
				String name = cu.getString(cu.getColumnIndex("dishname"));
				String intro = cu.getString(cu.getColumnIndex("dishintro"));
				String material = cu.getString(cu.getColumnIndex("dishmaterial"));
				String howtocook = cu.getString(cu.getColumnIndex("dishhowtocook"));
				String timetocook = cu.getString(cu.getColumnIndex("dishtimetofinish"));
				String dishpic = cu.getString(cu.getColumnIndex("dishpics"));
				 favorite = cu.getInt(cu.getColumnIndex("dishfavorite"));
				String basicmaterial = cu.getString(cu.getColumnIndex("dishbasicmaterial"));
				
					monan.setId(id);
				
				if(name != null)
					monan.setDishname(name);
				if(intro != null)
					monan.setIntro(intro);
				if(material != null)
					monan.setMaterial(material);
				if(basicmaterial  != null)
					monan.setBasicmaterial(basicmaterial);
				if(howtocook != null)
					monan.setHowtocook(howtocook);
				if(dishpic != null)
					monan.setDishpics(dishpic);
				if(timetocook != null)
					monan.setTimetocook(timetocook);
				if(favorite != 0)
					monan.setFavorite(favorite);
				
				toDayMonAn = monan;
			}
		}
		
		return toDayMonAn;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.an_gi_hom_nay, menu);
		return true;
	}

}
