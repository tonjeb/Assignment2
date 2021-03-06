package no.livedata.funrun.app.funrun.library;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 3;

	// Database Name
	private static final String DATABASE_NAME = "funrundb";

	// Activity table name
	private static final String TABLE_ACT = "act";

	// Act Table Columns names
	private static final String ACT_KEY_ID = "id";
	private static final String ACT_KEY_START = "start";
	private static final String ACT_KEY_DIST = "dist";
	private static final String ACT_KEY_TIME = "time";
	
	// Lap table name
	private static final String TABLE_LAP = "lap";

	// Lap Table Columns names
	public static final String LAP_KEY_ID = "id";
	public static final String LAP_KEY_TIME = "time";
	public static final String LAP_KEY_DIST = "dist";
	public static final String LAP_KEY_LAT = "lat";
	public static final String LAP_KEY_LON = "lon";
	public static final String LAP_KEY_ACT = "act";
	
	// Log table name
	private static final String TABLE_LOG = "log";

	// Log Table Columns names
	private static final String LOG_KEY_ID = "id";
	private static final String LOG_KEY_LAT = "lat";
	private static final String LOG_KEY_LON = "lon";
	private static final String LOG_KEY_TIME = "time";
	private static final String LOG_KEY_ALT = "alt";
	private static final String LOG_KEY_SPEED = "speed";
	private static final String LOG_KEY_ACT = "act";
	

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ACT_TABLE = "CREATE TABLE " + TABLE_ACT + "("
				+ ACT_KEY_ID + " INTEGER PRIMARY KEY," 
				+ ACT_KEY_START + " TEXT,"
				+ ACT_KEY_DIST + " INTEGER,"
				+ ACT_KEY_TIME + " TEXT" + ")";
		db.execSQL(CREATE_ACT_TABLE);
		
		String CREATE_LAP_TABLE = "CREATE TABLE " + TABLE_LAP + "("
				+ LAP_KEY_ID + " INTEGER PRIMARY KEY,"
				+ LAP_KEY_TIME + " TEXT,"
				+ LAP_KEY_DIST + " INTEGER,"
				+ LAP_KEY_LAT + " REAL,"
				+ LAP_KEY_LON + " REAL,"
				+ LAP_KEY_ACT + " INTEGER" + ")";
		db.execSQL(CREATE_LAP_TABLE);
		
		String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG + "("
				+ LOG_KEY_ID + " INTEGER PRIMARY KEY," 
				+ LOG_KEY_LAT + " REAL,"
				+ LOG_KEY_LON + " REAL,"
				+ LOG_KEY_TIME + " TEXT,"
				+ LOG_KEY_ALT + " REAL,"
				+ LOG_KEY_SPEED + " REAL,"
				+ LOG_KEY_ACT + " INTEGER" + ")";
		db.execSQL(CREATE_LOG_TABLE);
	}

	// Upgrading database
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);

		// Create tables again
		onCreate(db);
	}
	
	/**
	 * insert lap to db
	 * @param newLap a Lap object to insert
	 */
	public void insertLap(Lap newLap) {
		// get parameters
		long time = newLap.getTime();
		double dist = newLap.getDist();
		double lat = newLap.getLat();
		double lon = newLap.getLon();
		int act = newLap.getAct();
		
		// get db
		SQLiteDatabase db = this.getWritableDatabase();
		// add values
		ContentValues insert = new ContentValues(); 
		insert.put(LAP_KEY_TIME, time);
		insert.put(LAP_KEY_DIST, dist);
		insert.put(LAP_KEY_LAT, lat);
		insert.put(LAP_KEY_LON, lon);
		insert.put(LAP_KEY_ACT, act);

		// Prepared statement + sqlite 
		db.insert(TABLE_LAP, null, insert);
		
		db.close();
	}	
	
	/**
	 * insert activity
	 * @param newActivity act object to insert
	 * @returnactivity id
	 */
	public int insertActivity(Act newActivity) {
		// get params
		long time = newActivity.getTime();
		long start = newActivity.getStart();
		double dist = newActivity.getDist();
		
		SQLiteDatabase db = this.getWritableDatabase();
		// add params
		ContentValues insert = new ContentValues(); 
		insert.put(ACT_KEY_TIME, time);
		insert.put(ACT_KEY_START, start);
		insert.put(ACT_KEY_DIST, dist);

		// Prepared statement + sqlite 
		int insId = (int) db.insert(TABLE_ACT, null, insert);
		db.close();
		
		return insId;
	}	
	
	/**
	 * insert logg
	 * @param newLogg Logg objecty to insert
	 */
	public void insertLogg(Logg newLogg) {
		// get params
		double lat = newLogg.getLat();
		double lon = newLogg.getLon();
		long time = newLogg.getTime();
		double alt = newLogg.getAlt();
		double speed = newLogg.getSpeed();
		int act = newLogg.getAct();
		
		SQLiteDatabase db = this.getWritableDatabase();
		// add values
		ContentValues insert = new ContentValues(); 
		insert.put(LOG_KEY_LAT, lat);
		insert.put(LOG_KEY_LON, lon);
		insert.put(LOG_KEY_TIME, time);
		insert.put(LOG_KEY_ALT, alt);
		insert.put(LOG_KEY_SPEED, speed);
		insert.put(LOG_KEY_ACT, act);

		// Prepared statement + sqlite 
		db.insert(TABLE_LOG, null, insert);
		db.close();	
	}	
	
	/**
	 * Update activity
	 * @param id the id of the activity to update
	 * @param time the time to set
	 * @param dist the distance to set
	 */
	public void updateActivity(int id, long time, int dist) {	
		SQLiteDatabase db = this.getWritableDatabase();
		String strFilter = ACT_KEY_ID + "=" + id;
		
		// add vaalues
		ContentValues args = new ContentValues();
		args.put(ACT_KEY_TIME, time);
		args.put(ACT_KEY_DIST, dist);
		
		// update db
		db.update(TABLE_ACT, args, strFilter, null);
		db.close();
	}
	
	/**
	 * get all laps for activity
	 * @param id of activity
	 * @return ArrayList of laps
	 */
    public ArrayList<Lap> getLaps(int id){
    	ArrayList<Lap> output = new ArrayList<Lap>(); // the return array
        
        String selectQuery = "SELECT * FROM " + TABLE_LAP + " WHERE " + LAP_KEY_ACT + "=" + id; 
      
        // getredable db
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // cursor to go through db
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // if table has rows
            do {
            	output.add(
            		new Lap(	
            			cursor.getInt(cursor.getColumnIndex(LAP_KEY_ID)),
            			cursor.getLong(cursor.getColumnIndex(LAP_KEY_TIME)),
            			cursor.getInt(cursor.getColumnIndex(LAP_KEY_DIST)),
            			cursor.getDouble(cursor.getColumnIndex(LAP_KEY_LAT)),
            			cursor.getDouble(cursor.getColumnIndex(LAP_KEY_LON)),
            			cursor.getInt(cursor.getColumnIndex(LAP_KEY_ACT))
            		)
            	);
            } while (cursor.moveToNext()); // move cursor to next row
        }
        
        // close cursor and db connections
        cursor.close();
        db.close();
        
        return output; // return the array of all data
    }

/**
 * get all activities
 * @return ArrayList of activities
 */
public ArrayList<Act> getActivities(){
	ArrayList<Act> output = new ArrayList<Act>(); // the return array
    
    String selectQuery = "SELECT * FROM " + TABLE_ACT;
  
    // getredable db
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null); // cursor to go through db
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) { // if table has rows
        do {
        	output.add(
        		new Act(	
        			cursor.getInt(cursor.getColumnIndex(ACT_KEY_ID)),
        			cursor.getLong(cursor.getColumnIndex(ACT_KEY_TIME)),
        			cursor.getLong(cursor.getColumnIndex(ACT_KEY_START)),
        			cursor.getInt(cursor.getColumnIndex(ACT_KEY_DIST))
        		)
        	);
        } while (cursor.moveToNext()); // move cursor to next row
    }
    
    // close cursor and db connections
    cursor.close();
    db.close();
    
    return output; // return the array of all data
}


/**
 * get all log entries for activity
 * @param id of activity
 * @return ArrayList of Log objects
 */
public ArrayList<Logg> getLog(int id){
	ArrayList<Logg> output = new ArrayList<Logg>(); // the return array
    
    String selectQuery = "SELECT * FROM " + TABLE_LOG + " WHERE " + LOG_KEY_ACT + "=" + id;
    
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
        			cursor.getLong(cursor.getColumnIndex(LOG_KEY_TIME)),
        			cursor.getDouble(cursor.getColumnIndex(LOG_KEY_ALT)),
        			cursor.getDouble(cursor.getColumnIndex(LOG_KEY_SPEED)),
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

