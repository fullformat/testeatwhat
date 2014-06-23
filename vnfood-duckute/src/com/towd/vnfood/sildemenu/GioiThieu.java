package com.towd.vnfood.sildemenu;

import com.towd.vnfood.R;
import com.towd.vnfood.R.layout;
import com.towd.vnfood.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class GioiThieu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gioi_thieu);
		
		TextView tv = (TextView) findViewById(R.id.tv_GioiThieu);
		
		tv.setText("Ứng dụng hữu ích chuyên về:\nBộ sưu tập các món ăn\nMón ăn đặc sắc\nĐủ loại danh mục\nTư vấn món ăn\nCác món ăn kị nhau cần tránh\nCập nhật thường xuyên\nBản quyền thuộc về nhóm phát triển\nĐóng góp ý kiến vui lòng gửi về: feedback.angihomnay@gmail.com");
	}

	
}
