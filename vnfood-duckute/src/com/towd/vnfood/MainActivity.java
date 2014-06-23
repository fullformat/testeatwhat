package com.towd.vnfood;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.towd.vnfood.datamanager.CustomGridViewAdapter;
import com.towd.vnfood.datamanager.CustomSlidingmenuAdapter;
import com.towd.vnfood.sildemenu.AnGiHomNay;
import com.towd.vnfood.sildemenu.DieuCanBiet;
import com.towd.vnfood.sildemenu.GioiThieu;
import com.towd.vnfood.sildemenu.MonanListFavoriteActivity;
import com.towd.vnfood.utils.Constants;
import com.towd.vnfood.utils.Utils;

public class MainActivity extends Activity {

	private Utils utils;

	// Grd view
	GridView gridView;
	private int columnWidth;
	private int rowHeight;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter customGridAdapter;

	// side bar
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	String[] menutitles;
	TypedArray menuIcons;
	private List<RowItem> rowItems;
	 private CustomSlidingmenuAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		utils = new Utils(this);

		// side bar
		mTitle = mDrawerTitle = getTitle();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		menutitles = getResources().getStringArray(R.array.setting_array);
		menuIcons = getResources().obtainTypedArray(R.array.icons);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		

		rowItems = new ArrayList<RowItem>();

		  for (int i = 0; i < menutitles.length; i++) {
		   RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
		     i, -1));
		   rowItems.add(items);
		  }

		  menuIcons.recycle();
		  
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//				R.layout.drawer_list_item, mPlanetTitles));
		adapter = new CustomSlidingmenuAdapter(this,rowItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//mDrawerLayout.setBackgroundResource(R.color.maucam3);

		if (savedInstanceState == null) {
			selectItem(0);
		}
		// end side bar

		gridView = (GridView) findViewById(R.id.gridView1);
		InitilizeGridLayout();

		Bitmap monbac001 = getBitmapFromAssets("MonBac/monbac001.jpg",
				columnWidth);
		Bitmap montrung001 = getBitmapFromAssets("MonTrung/montrung001.jpg",
				columnWidth);
		Bitmap monnam001 = getBitmapFromAssets("MonNam/monnam001.jpg",
				columnWidth);
		Bitmap monchay001 = getBitmapFromAssets("MonChay/monchay001.jpg",
				columnWidth);
		Bitmap monanthai001 = getBitmapFromAssets("MonAnThai/monanthai001.jpg",
				columnWidth);
		Bitmap monche001 = getBitmapFromAssets("MonChe/monche001.jpg",
				columnWidth);
		Bitmap monbanh001 = getBitmapFromAssets("MonBanh/monbanh001.jpg",
				columnWidth);
		Bitmap MonNuocNgoai = getBitmapFromAssets(
				"MonNuocNgoai/monnuocngoai001.jpg", columnWidth);
		Bitmap douong001 = getBitmapFromAssets("DoUong/douong001.jpg",
				columnWidth);

		gridArray.add(new Item(monbac001, "Món miền Bắc"));
		gridArray.add(new Item(montrung001, "Món miền Trung"));
		gridArray.add(new Item(monnam001, "Món miền Nam"));
		gridArray.add(new Item(monchay001, "Món chay"));
		gridArray.add(new Item(monanthai001, "Món an thai"));
		gridArray.add(new Item(monche001, "Món chè"));
		gridArray.add(new Item(monbanh001, "Món bánh"));
		gridArray.add(new Item(MonNuocNgoai, "Món nước ngoài"));
		gridArray.add(new Item(douong001, "Đồ uống"));

		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid,
				gridArray);
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				int category = position + 1;
				Intent in = new Intent(MainActivity.this,
						MonanListActivity.class);
				in.putExtra("LOAIMON", category);
				startActivity(in);
			}
		});
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				Constants.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((Constants.NUM_OF_COLUMNS + 1) * padding)) / Constants.NUM_OF_COLUMNS);

		gridView.setNumColumns(Constants.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		// gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

	// load image from assets
	public Bitmap getBitmapFromAssets(String fileName, int WIDTH) {

		AssetManager assetManager = getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Bitmap bitmap = BitmapFactory.decodeStream(istr);
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(istr, null, o);

		final int REQUIRED_WIDTH = WIDTH;
		final int REQUIRED_HIGHT = WIDTH;
		int scale = 1;
		while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
				&& o.outHeight / scale / 2 >= REQUIRED_HIGHT)
			scale *= 2;

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(istr, null, o2);
	}

	// side bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		// thằng này sẽ đc focus khi mở app hoặc ấn menu button
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		switch (item.getItemId()) {
		case R.id.view_marked:
			Intent i = new Intent(this, MonanListFavoriteActivity.class);
			startActivity(i);
			return true;
		}
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	// Cho nay de set item
	private void selectItem(int position) {

		mDrawerList.setItemChecked(position, true);
		setTitle(menutitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		// menu slide
		switch (position) {
		case 0:
			// Intent in0 = new Intent(getApplicationContext(),
			// MainActivity.class);
			// startActivity(in0);
			break;
		// ăn gì hôm nay
		case 1:
			Intent in1 = new Intent(getApplicationContext(), AnGiHomNay.class);
			startActivity(in1);
			break;
		// món yêu thích
		case 2:
			Intent in2 = new Intent(getApplicationContext(),
					MonanListFavoriteActivity.class);
			startActivity(in2);
			break;
		
		// điều cần biết
		case 3:
			Intent in3 = new Intent(getApplicationContext(), DieuCanBiet.class);
			startActivity(in3);
			break;
		
		// giới thiệu
		case 4:
			Intent in4 = new Intent(getApplicationContext(), GioiThieu.class);
			startActivity(in4);
			break;

		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	// end side bar
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU){
			boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
			if(drawerOpen){
				mDrawerLayout.closeDrawer(mDrawerList);
			} else
				mDrawerLayout.openDrawer(mDrawerList);
		}
		return super.onKeyUp(keyCode, event);
	}
}
