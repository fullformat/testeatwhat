package com.towd.vnfood;

import java.io.IOException;
import java.util.ArrayList;

import android.R.drawable;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.towd.vnfood.datamanager.ListMonAnAdapter;
import com.towd.vnfood.datamanager.MyDataBaseManager;
import com.towd.vnfood.sildemenu.MonanListFavoriteActivity;

// danh sách món ăn theo từng loại 

public class MonanListActivity extends ListActivity {

	protected static final String INTENT_KEY = "send_intent";
	MonanObject st = null; 													
	ArrayList<MonanObject> lvdata = null; 										
	
	private int category_monan;
	
	MyDataBaseManager db;
	ListMonAnAdapter adapter;
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
		
		category_monan = getIntent().getExtras().getInt("LOAIMON");
		
		ListView listView = this.getListView();
		
		lvdata = loadMonAn();
		
		
		// chuyển đẩy data vào adapter để chuyển đổi data
		adapter = new ListMonAnAdapter(this, lvdata);
		// nạp dữ liệu vào ListView
		setListAdapter(adapter);
		
		getListView().setTextFilterEnabled(true);
		
		LayoutAnimationController controller 
		   = AnimationUtils.loadLayoutAnimation(
		     this, R.anim.list_layout_controller);
		  getListView().setLayoutAnimation(controller);
		  
//		  getListView().setBackgroundColor(Color.rgb(36, 33, 32));
		  getListView().setBackgroundResource(R.drawable.hinhnen_2);
		  
		  //getListView().setBackgroundDrawable(R.drawable.hinhnen_2);
		  
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		MonanObject monan = lvdata.get(position);
		Intent intent = new Intent(MonanListActivity.this, MonanSingleActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(INTENT_KEY, monan);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		View v = (View) menu.findItem(R.id.search).getActionView();
		
        EditText txtSearch = ( EditText ) v.findViewById(R.id.txt_search);
		
        txtSearch.addTextChangedListener(new TextWatcher() {
            
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"]" +
						" - Count ["+count+"]");
				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					 adapter.resetData();
				}

				adapter.getFilter().filter(s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
        
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.search:
			return true;
		case R.id.view_marked:
			Intent i = new Intent(this,MonanListFavoriteActivity.class);
			startActivity(i);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	public ArrayList<MonanObject> loadMonAn() {
		ArrayList<MonanObject> listMon = new ArrayList<MonanObject>();
		int favorite = 0;
		// dựa vào category để lấy ra danh sách các món ăn thuộc category đó , dùng sqlite
		Cursor cu = db.getListDish(category_monan);
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
				String namesearch = cu.getString(cu.getColumnIndex("dishnamesearch"));
				
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
				if(namesearch != null){
					monan.setNamesearch(namesearch);
				}
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
