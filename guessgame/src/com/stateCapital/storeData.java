//Author:Zhanhong Zheng
//Course:cs211d
//program name:storeData.java
//objective:using sqlite to store data 

package com.stateCapital;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;

public class storeData extends SQLiteOpenHelper
{
    private static final String DBNAME="my.db";
    static final String STATENAME="states",CAPITALNAME="capitals";
    
    public storeData(Context c)
    {
    	super(c,DBNAME,null,1);
    }

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String command = "create table info(_id integer primary key autoincrement,states text, capitals text);";
		db.execSQL(command);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("drop table if exists info");
		onCreate(db);
	}
	
	public void add(SQLiteDatabase db,String[] states,String[] capitals)
	{
		ContentValues cv = new ContentValues();
		for(int i=0;i<states.length;i++)
		{
			cv.put(STATENAME, states[i]);
			cv.put(CAPITALNAME, capitals[i]);
			db.insert("info", null, cv);
		}
	}
	
	public String getState(SQLiteDatabase db,int num)
	{
		String command = ("select * from info where _id = "+num+";");
		String stateName = "";
		Cursor c = db.rawQuery(command,null);
		while(c.moveToNext())
		{
			int col = c.getColumnIndex(STATENAME);
			stateName = c.getString(col);
		}
		return(stateName);
	}	
	
	public String getCapital(SQLiteDatabase db,int num)
	{
		String command = ("select * from info where _id = "+num+";");
		String capitalName = "";
		Cursor c = db.rawQuery(command, null);
		while(c.moveToNext())
		{	
			int col = c.getColumnIndex(CAPITALNAME);
			capitalName = c.getString(col);
		}
		return(capitalName);
	}
}
