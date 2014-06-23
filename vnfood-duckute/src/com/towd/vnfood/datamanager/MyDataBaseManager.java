package com.towd.vnfood.datamanager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseManager extends SQLiteOpenHelper {

	private static String DB_PATH="/data/data/com.towd.vnfood/databases/";
	private static String DB_NAME="datadish";
	private static final int DB_VERSION=1;
	private static final String TABLE_NAME="dish";

	private SQLiteDatabase myDatabase;
	private final Context myContext;
	
	
	Context mcontext;
	
	public MyDataBaseManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		myContext = context;
	}
	public void openDatabase() throws SQLException
	{
		String myPath = DB_PATH + DB_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
	}
	public synchronized void close()
	{
		if(myDatabase!= null)		
			myDatabase.close();		
		super.close();
	}
	private boolean checkDatabase()
	{
		SQLiteDatabase checkDB = null;
		try
		{
			String myPath = DB_PATH + DB_NAME;
			checkDB =SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLException e)
		{
			
		}
		if (checkDB!= null)
		checkDB.close();
		return checkDB != null ? true : false;
	}
	private void copyDatabase() throws IOException
	{
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while((length = myInput.read(buffer))>0)
		{
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	public void createDatabase() throws IOException
	{
		boolean dbExist = checkDatabase();
		
		if(dbExist)
		{
			
		}
		else
		{
			this.getReadableDatabase();
			try
			{
				copyDatabase();
			}catch (IOException e)
			{
				throw new Error("Error copy database");
			}
			
		}
	}
	public Cursor getListDish(int categoryid)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor contro =db.rawQuery("select * from dish where categoryid =" + categoryid,null);
		return contro;
	}
	public Cursor getListDishFavorite()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor contro =db.rawQuery("select * from dish where dishfavorite = 1" ,null);
		return contro;
	}
	public Cursor getTodayDish(int dishid)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor contro =db.rawQuery("select * from dish where dishid = " + dishid ,null);
		return contro;
	}
	
	public int updateFavorite (int id, int key){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put("dishfavorite", key);
		
		return db.update(TABLE_NAME, content, "dishid = " + id,
				null);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	

}
