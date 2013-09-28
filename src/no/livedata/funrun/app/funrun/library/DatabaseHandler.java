package no.livedata.funrun.app.funrun.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Element;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "funrundb";

	// Activity table name
	private static final String TABLE_ACT = "act";

	// Act Table Columns names
	private static final String ACT_KEY_ID = "id";
	private static final String ACT_KEY_START = "start";
	private static final String ACT_KEY_TIME = "time";
	
	// Lap table name
	private static final String TABLE_LAP = "lap";

	// Lap Table Columns names
	public static final String LAP_KEY_ID = "id";
	public static final String LAP_KEY_TIME = "time";
	public static final String LAP_KEY_ACT = "act";
	
	// Log table name
	private static final String TABLE_LOG = "log";

	// Log Table Columns names
	private static final String LOG_KEY_ID = "id";
	private static final String LOG_KEY_LAT = "lat";
	private static final String LOG_KEY_LON = "lon";
	private static final String LOG_KEY_TIME = "time";
	private static final String LOG_KEY_ALT = "alt";
	private static final String LOG_KEY_ACT = "act";
	

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ACT_TABLE = "CREATE TABLE " + TABLE_ACT + "("
				+ ACT_KEY_ID + " INTEGER PRIMARY KEY," 
				+ ACT_KEY_START + " TEXT,"
				+ ACT_KEY_TIME + " TEXT" + ")";
		db.execSQL(CREATE_ACT_TABLE);
		
		String CREATE_LAP_TABLE = "CREATE TABLE " + TABLE_LAP + "("
				+ LAP_KEY_ID + " INTEGER PRIMARY KEY,"
				+ LAP_KEY_TIME + " TEXT,"
				+ LAP_KEY_ACT + " INTEGER" + ")";
		db.execSQL(CREATE_LAP_TABLE);
		
		String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG + "("
				+ LOG_KEY_ID + " INTEGER PRIMARY KEY," 
				+ LOG_KEY_LAT + " REAL,"
				+ LOG_KEY_LON + " REAL,"
				+ LOG_KEY_TIME + " TEXT,"
				+ LOG_KEY_ALT + " REAL,"
				+ LOG_KEY_ACT + " INTEGER" + ")";
		db.execSQL(CREATE_LOG_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);

		// Create tables again
		onCreate(db);
	}
	


    public ArrayList<Lap> getLap(int id){
    	ArrayList<Lap> output = new ArrayList<Lap>(); // the return array
        
        String selectQuery = "SELECT * FROM " + TABLE_LAP + " WHERE LAP_KEY_ID=" + id; //HUSK sjekk om den eksistrer
      
        // getredable db
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // cursor to go through db
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // if table has rows
            do {
            	output.add(
            		new Lap(	
            			(cursor.getInt(cursor.getColumnIndex(LAP_KEY_ID))),
            			cursor.getString(cursor.getColumnIndex(LAP_KEY_TIME)),
            			cursor.getString(cursor.getColumnIndex(LAP_KEY_ACT))
            		)
            	);
            } while (cursor.moveToNext()); // move cursor to next row
        }
        
        // close cursor and db connections
        cursor.close();
        db.close();
        
        return output; // return the array of all data
    }


//Set 
    public ArrayList<Lap> setLap(){
    	ArrayList<Lap> output = new ArrayList<Lap>(); // the return array
        
        String selectQuery = "SELECT * FROM " + TABLE_LAP; // select all rows from Postable
      
        // getredable db
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // cursor to go through db
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // if table has rows
            do {
            	output.add(
            		new Lap(	
            			(cursor.getInt(cursor.getColumnIndex(LAP_KEY_ID))),
            			cursor.getString(cursor.getColumnIndex(LAP_KEY_TIME)),
            			cursor.getString(cursor.getColumnIndex(LAP_KEY_ACT))
            		)
            	);
            } while (cursor.moveToNext()); // move cursor to next row
        }
        
        // close cursor and db connections
        cursor.close();
        db.close();
        
        return output; // return the array of all data
    }


public ArrayList<Activity> getActivity(int id){
	ArrayList<Activity> output = new ArrayList<Activity>(); // the return array
    
    String selectQuery = "SELECT * FROM " + TABLE_ACT + " WHERE ACT_KEY_ID=" + id; // HUSK sjekk om den eksisterer
  
    // getredable db
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null); // cursor to go through db
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) { // if table has rows
        do {
        	output.add(
        		new Activity(	
        			(cursor.getInt(cursor.getColumnIndex(ACT_KEY_ID))),
        			cursor.getString(cursor.getColumnIndex(ACT_KEY_TIME)),
        			cursor.getString(cursor.getColumnIndex(ACT_KEY_START))
        		)
        	);
        } while (cursor.moveToNext()); // move cursor to next row
    }
    
    // close cursor and db connections
    cursor.close();
    db.close();
    
    return output; // return the array of all data
}


public ArrayList<Logg> getLog(int id){
	ArrayList<Logg> output = new ArrayList<Logg>(); // the return array
    
    String selectQuery = "SELECT * FROM " + TABLE_LOG + " WHERE LOG_KEY_ID=" + id; //HUSK sjekk om den eksisterer
  
    // getredable db
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null); // cursor to go through db
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) { // if table has rows
        do {
        	output.add(
        		new Logg(	
        			(cursor.getInt(cursor.getColumnIndex(LOG_KEY_ID))),
        			cursor.getDouble(cursor.getColumnIndex(LOG_KEY_LAT)),
        			cursor.getDouble(cursor.getColumnIndex(LOG_KEY_LON)),
        			cursor.getString(cursor.getColumnIndex(LOG_KEY_TIME)),
        			cursor.getDouble(cursor.getColumnIndex(LOG_KEY_ALT)),
        			cursor.getInt(cursor.getColumnIndex(LOG_KEY_ACT))
        		)
        	);
        } while (cursor.moveToNext()); // move cursor to next row
    }
    
    // close cursor and db connections
    cursor.close();
    db.close();
    
    return output; // return the array of all data
}
}

