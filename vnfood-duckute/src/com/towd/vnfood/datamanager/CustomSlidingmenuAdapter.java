package com.towd.vnfood.datamanager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.towd.vnfood.R;
import com.towd.vnfood.R.dimen;
import com.towd.vnfood.R.drawable;
import com.towd.vnfood.RowItem;

public class CustomSlidingmenuAdapter extends BaseAdapter {

	Context context;
	 List<RowItem> rowItem;

	 public CustomSlidingmenuAdapter(Context context, List<RowItem> rowItem) {
	  this.context = context;
	  this.rowItem = rowItem;
	 }


	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {

	if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater) context
	                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
	        }

	        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
	        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

	        RowItem row_pos = rowItem.get(position);
	        // setting the image resource and title
	        imgIcon.setImageResource(row_pos.getIcon());
	        txtTitle.setText(row_pos.getTitle());
	        convertView.setPadding(2, 2, 2, 2);
	        return convertView;
	 }

	 @Override
	 public int getCount() {
	  return rowItem.size();
	 }

	 @Override
	 public Object getItem(int position) {
	  return rowItem.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
	  return rowItem.indexOf(getItem(position));
	 }

	}