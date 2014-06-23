package com.towd.vnfood.sildemenu;

import java.io.IOException;
import java.util.ArrayList;

import com.towd.vnfood.MonanObject;
import com.towd.vnfood.MonanSingleActivity;
import com.towd.vnfood.R;
import com.towd.vnfood.R.anim;
import com.towd.vnfood.R.menu;
import com.towd.vnfood.datamanager.ListMonAnAdapter;
import com.towd.vnfood.datamanager.MyDataBaseManager;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

// danh sách món ăn theo từng loại 

public class MonanListFavoriteActivity extends ListActivity {
	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		super.setTitle("Danh sách món ăn yêu thích!");
	}

	protected static final String INTENT_KEY = "send_intent";
	MonanObject st = null; 													
	ArrayList<MonanObject> lvdata = null; 										
	
	MyDataBaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new MyDataBaseManager(this);
		try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Lỗi tạo cơ sỡ dữ liệu", Toast.LENGTH_SHORT)
					.show();
		}
		
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
		
		
		ListView listView = this.getListView();
		
		lvdata = loadMonAn();
		
		// chuyển đẩy data vào adapter để chuyển đổi data
		ListMonAnAdapter adapter = new ListMonAnAdapter(this, lvdata);
		// nạp dữ liệu vào ListView
		setListAdapter(adapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		MonanObject monan = lvdata.get(position);
		Intent intent = new Intent(MonanListFavoriteActivity.this, MonanSingleActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(INTENT_KEY, monan);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public ArrayList<MonanObject> loadMonAn() {
		ArrayList<MonanObject> listMon = new ArrayList<MonanObject>();
		int favorite = 0;
		
		// dựa vào category để lấy ra danh sách các món ăn thuộc category đó , dùng sqlite
		Cursor cu = db.getListDishFavorite();
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
				
				listMon.add(monan);
			}
		}
		
		
		
		return listMon;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
	}
}
