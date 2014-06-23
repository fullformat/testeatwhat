package com.towd.vnfood.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class Utils {
	
	private Context _context;
	 
    // constructor
    public Utils(Context context) {
        this._context = context;
    }
    
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
    public int getScreenHeight() {
        int rowheight;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        rowheight = point.y;
        return rowheight;
    }
    public Bitmap getBitmapFromAssets(String fileName) {
    	
    	Resources r = _context.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        		Constants.GRID_PADDING, r.getDisplayMetrics());
    	
    	int WIDTH = (int) ((getScreenWidth() - ((Constants.NUM_OF_COLUMNS + 1) * padding)) / Constants.NUM_OF_COLUMNS);
    	
        AssetManager assetManager = _context.getAssets();
        InputStream istr = null;
		try {
			 istr = assetManager.open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        Bitmap bitmap = BitmapFactory.decodeStream(istr);
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
    public boolean assetExists(String path) {
	    boolean bAssetOk = false;
	    try {
	        InputStream stream = _context.getAssets().open(path);
	        stream.close();
	        bAssetOk = true;
	    } catch (FileNotFoundException e) {
	        Log.w("IOUtilities", "assetExists failed: "+e.toString());
	    } catch (IOException e) {
	        Log.w("IOUtilities", "assetExists failed: "+e.toString());
	    }
	    return bAssetOk;
	}
    
    
    public String readFromFile(String filename) {
    	BufferedReader reader = null;
    	String text = "";
        AssetManager assetManager = _context.getAssets();
    	try {
    	    reader = new BufferedReader(
    	        new InputStreamReader(assetManager.open(filename)));

    	    // do reading, usually loop until end of file reading  
    	    String mLine = reader.readLine();
    	    while (mLine != null) {
    	       //process line
    	       mLine = reader.readLine();
    	       if(mLine!= null){
    	       text += mLine +"\n";
    	       }
    	    }
    	} catch (IOException e) {
    	    //log the exception
    	} finally {
    	    if (reader != null) {
    	        try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
    	}
  		return text;
     }
}
