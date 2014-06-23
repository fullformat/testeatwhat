package com.towd.vnfood.datamanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.R.drawable;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.towd.vnfood.MonanObject;
import com.towd.vnfood.R;
import com.towd.vnfood.utils.Constants;
import com.towd.vnfood.utils.Utils;

public class ListMonAnAdapter extends BaseAdapter implements Filterable {
	
	
	private Activity activity;
	private ArrayList<MonanObject> arrSMonAn;
	MyDataBaseManager dataManager = new MyDataBaseManager(activity);
	
	//MyDataBaseManager dataManager ;
	 
	 Utils utils ;
	 int WIDTH;
	 
	 // filter
		private Filter planetFilter;
		private ArrayList<MonanObject> origPlanetList;
	 
	 public ListMonAnAdapter(Activity activity, ArrayList<MonanObject> arrStudent) {
	  this.activity = activity;
	  this.arrSMonAn = arrStudent;
	  utils = new Utils(activity);
	  
	  Resources r = activity.getResources();
	        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
	          Constants.GRID_PADDING, r.getDisplayMetrics());
	     
	     WIDTH = (int) (((utils.getScreenWidth() - ((Constants.NUM_OF_COLUMNS + 1) * padding)) 
	    		 / Constants.NUM_OF_COLUMNS)/2);
	     dataManager = new MyDataBaseManager(activity);
	     try {
	   dataManager.createDatabase();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	     origPlanetList = arrStudent;
	 }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrSMonAn.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrSMonAn.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MonanObject mon = arrSMonAn.get(position);
		LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
		View rowItem = inflater.inflate(R.layout.row_listview, null);

		ImageView imvMonAn = (ImageView) rowItem.findViewById(R.id.imv_MonAn_rowlist);
		TextView tvName = (TextView) rowItem.findViewById(R.id.tv_TenMonAn_rowview);
		TextView tvTime = (TextView) rowItem.findViewById(R.id.tv_ThoiGianNau_rowview);
		TextView tvBasicMaterial = (TextView) rowItem.findViewById(R.id.tv_MoTaCoBanNguyenLieu_rowview);
		final ImageView imv_favorite = (ImageView) rowItem.findViewById(R.id.imv_bookmarks);
		
 		
		if(mon.getFavorite()== 1){ 
			imv_favorite.setImageResource(drawable.star_big_on);
			
		}if(mon.getFavorite()!= 1){
			imv_favorite.setImageResource(drawable.star_big_off);;
		}
		// setonclick listener
		imv_favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				// TODO Auto-generated method stub
				int key_id = mon.getId();
				
				if(mon.getFavorite()== 1){ 
					mon.setFavorite(0);
					imv_favorite.setImageResource(drawable.star_big_off);
					if(dataManager.updateFavorite(key_id, 0)> 0){
					Toast.makeText(activity, "Bỏ đánh dấu!", Toast.LENGTH_SHORT).show();
						
					}
					
				}else{
					mon.setFavorite(1);
					imv_favorite.setImageResource(drawable.star_big_on);;
					
					if(dataManager.updateFavorite(key_id, 1)> 0){
					Toast.makeText(activity, "Đã đánh dấu!", Toast.LENGTH_SHORT).show();
						
					}
				}
				
			}
		});
	
		// set bacground for item listview
		if(position%2 == 1){
			rowItem.setBackgroundResource(R.color.maucam4);
			
		}
		else{
			rowItem.setBackgroundResource(R.color.maucam3);
		}
		if(mon.getDishname() != null)
			tvName.setText(mon.getDishname().toString());
		if(mon.getTimetocook() != null)
			tvTime.setText( "Thời gian: "+ mon.getTimetocook().toString());
		if(mon.getBasicmaterial()!= null) 
			
			tvBasicMaterial.setText( "Nguyên liệu chính: "+ mon.getBasicmaterial().toString());
		
		String dishpc = mon.getDishpics();
		
		String link = "DoUong/" + dishpc;;
		if(utils.assetExists("DoUong/" + dishpc)){
			
			link = "DoUong/" + dishpc;
			
		} else if(utils.assetExists("MonAnThai/" + dishpc)){
			link = "MonAnThai/" + dishpc;
			
		} else if(utils.assetExists("MonTrung/" + dishpc)){
			link = "MonTrung/" + dishpc;
			
		} else if(utils.assetExists("MonBac/" + dishpc)){
			link = "MonBac/" + dishpc;
			
		} else if(utils.assetExists("MonBanh/" + dishpc)){
			link = "MonBanh/" + dishpc;
			
		} else if(utils.assetExists("MonChay/" + dishpc)){
			link = "MonChay/" + dishpc;
			
		} else if(utils.assetExists("MonChe/" + dishpc)){
			link = "MonChe/" + dishpc;
			
		} else if(utils.assetExists("MonNam/" + dishpc)){
			link = "MonNam/" + dishpc;
			
		} else if(utils.assetExists("MonNuocNgoai/" + dishpc)){
			link = "MonNuocNgoai/" + dishpc;
			
		}
		Bitmap bitmap = utils.getBitmapFromAssets(link);
		bitmap = Bitmap.createScaledBitmap(bitmap, WIDTH, WIDTH, true);
		imvMonAn.setImageBitmap(bitmap);

		return rowItem;
	}

	public Bitmap getBitmapFromAssets(String fileName) {
    	
        AssetManager assetManager = activity.getAssets();
        InputStream istr = null;
		try {
			 istr = assetManager.open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }
	
	@Override
	public Filter getFilter() {
		if (planetFilter == null)
			planetFilter = new PlanetFilter();

		return planetFilter;
	}



	private class PlanetFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = origPlanetList;
				results.count = origPlanetList.size();
			}
			else {
				// We perform filtering operation
				ArrayList<MonanObject> nPlanetList = new ArrayList<MonanObject>();

				for (MonanObject p : arrSMonAn) {
					if (p.getNamesearch().toUpperCase().startsWith(constraint.toString().toUpperCase()))
						nPlanetList.add(p);
				}

				results.values = nPlanetList;
				results.count = nPlanetList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// Now we have to inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				arrSMonAn = (ArrayList<MonanObject>) results.values;
				notifyDataSetChanged();
			}

		}

	}
	public void resetData() {
		arrSMonAn = origPlanetList;
	}
}
