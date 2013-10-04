package no.livedata.funrun.app.funrun;

import java.util.Locale;

import no.livedata.funrun.app.funrun.library.Act;
import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Lap;
import no.livedata.funrun.app.funrun.library.Logg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	
	LoggerReceiver logReceiver;
	Intent serviceIntent;
	
	Button startButton;
	Button lapButton;
	TextView timeView;
	TextView distView;
	TextView lapView;
	TextView speedView;
	
	// Menu
	Button mBtnMap;
	Button mBtnLap;
	Button mBtnHome;
	Button mBtnHist;
	
	// Timer
	Handler mHandler = new Handler();
	long startTime;
	long stopTime;
	long elapsedTime;
	final int REFRESH_RATE = 100;
	boolean stopped = false;
	
	int distance = 0;
	int activity = -1;
	int time = 0;
	
	int currentLap = 0;
	double lat = 0.0;
	double lon = 0.0;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// get screen orientation
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.main); // use standard layout if orientation is portrait
	    } else {
	    	setContentView(R.layout.main_l); // use landscape layout if orientation is landscape (not portrait)
	    }
		
		logReceiver = new LoggerReceiver();
	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(LoggerService.UPDATE_UI);
	    registerReceiver(logReceiver, intentFilter);
		
		serviceIntent = new Intent(Main.this, LoggerService.class); 
		
		startButton = (Button) findViewById(R.id.StartButton);
		lapButton = (Button) findViewById(R.id.LapButton);
		timeView = (TextView) findViewById(R.id.TimeView);
		distView = (TextView) findViewById(R.id.DistView);
		lapView = (TextView) findViewById(R.id.LapView);
		speedView = (TextView) findViewById(R.id.speedView);
		
		// menu
		mBtnMap = (Button) findViewById(R.id.mBtnMap);
		mBtnLap = (Button) findViewById(R.id.mBtnLap);
		mBtnHist = (Button) findViewById(R.id.mBtnHist);
		
		startButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
		lapButton.getBackground().setColorFilter(0xFF00FFFF, PorterDuff.Mode.MULTIPLY);
		
		mBtnMap.setOnClickListener( new View.OnClickListener() {
			public void onClick (View v) {
				Intent mapInt = new Intent(getApplicationContext(), Map.class); 
	        	mapInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(mapInt); // start the activity
			}
		});
		
		startButton.setTag(1);
		startButton.setText(getResources().getString(R.string.start));
		startButton.setOnClickListener( new View.OnClickListener() {
			public void onClick (View v) {
				final int status =(Integer) v.getTag();
				if(status == 1) { // START
					startTime();
					newActivity();
					serviceIntent.putExtra("ACT", activity);
					startService(serviceIntent);
					Log.d("SE","Service started");
					
				    //take the time

					lapButton.setText(getResources().getString(R.string.lap));
					lapButton.setEnabled(true);
				    startButton.setText(getResources().getString(R.string.stop));
				    startButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
				    lapButton.getBackground().setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
				    v.setTag(0); //pause
				    lapButton.setTag(0); // set lap button to lap
				} else { // STOP
					stopTime();
					stopService(serviceIntent);
					

				    startButton.setText(getResources().getString(R.string.start));
				    lapButton.setText(getResources().getString(R.string.reset));
				    startButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
				    lapButton.getBackground().setColorFilter(0xFF00FFFF, PorterDuff.Mode.MULTIPLY);
				    v.setTag(1); //pause
				    lapButton.setTag(1); // set lap button to reset
				} 
			}
		});
		

		lapButton.setTag(1);
		lapButton.setText(getResources().getString(R.string.lap));
		lapButton.setEnabled(false);
		lapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick (View v) {
				final int status =(Integer) v.getTag();
				if(status == 1) { // RESET
					resetTime();
					closeActivity();
					//reset time for lap and save the time
				} else { // LAP
					newLap();
				} 
			}
		});
		
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	};
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(getResources().getString(R.string.quit))
	        .setMessage(getResources().getString(R.string.sure)) 
	        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	onDestroy();
	            finish();  
	            System.exit(0);
	        }

	    })
	    .setNegativeButton(getResources().getString(R.string.no), null)
	    .show();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(logReceiver);
		stopService(serviceIntent);
		closeActivity();
		super.onDestroy();
	}
	
	private void newActivity() {
		if (activity == -1) { // create new activity if no activity active
			// connect to db
	 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
	 		activity = db.insertActivity(new Act(
	 							0,
	 							(int)System.currentTimeMillis(),
	 							0,
	 							0
	 						));
	 		db.close();
	 		mBtnLap.setEnabled(true);
		}
	}
	
	private void closeActivity() {
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		db.updateActivity(activity,time,distance); // update current activity with time and distance
 		db.close();
 		activity = -1;
 		mBtnLap.setEnabled(false);
	}
	
	private void newLap() {
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		db.insertLap(new Lap ( // insert new lap to db
 							0,
 							time,
 							distance,
 							lat,
 							lon,
 							activity
 						));
 		db.close();
 		currentLap = time;
 		lapView.setText(timeToString(time));
	}
	
	private class LoggerReceiver extends BroadcastReceiver{
		 
		 @Override
		 public void onReceive(Context con, Intent in) {

			  long dist = in.getLongExtra("DIST", -1);
			  double speed = in.getDoubleExtra("SPEED", -1);
			  lat = in.getDoubleExtra("LAT", 0.0);
			  lon = in.getDoubleExtra("LON", 0.0);
			  
			  if (dist != -1) {
				  distance = (int) dist;
				  distView.setText((double)dist/1000+"");
			  }
			  
			  if (speed != -1) {
				  speedView.setText(speed+"");
			  }
		  
		 }
		 
	}
	
	/* timer */
    private void startTime (){
    	if(stopped){
    		startTime = System.currentTimeMillis() - elapsedTime; 
    	}
    	else{
    		startTime = System.currentTimeMillis();
    	}
    	mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }
    
    public void stopTime (){
    	mHandler.removeCallbacks(startTimer);
    	stopped = true;
    }
    
    public void resetTime (){
    	stopped = false;
    	timeView.setText("00:00");
    	lapView.setText("00:00");
    	distView.setText("0.0");
    	speedView.setText("0.0");
    }
    
    private Runnable startTimer = new Runnable() {
 	   public void run() {
 		   elapsedTime = System.currentTimeMillis() - startTime;
 		   timeView.setText(timeToString(elapsedTime));
 		   if (currentLap != 0) {
 			   lapView.setText(timeToString(elapsedTime-currentLap));
 		   }
 		   time = (int) elapsedTime;
 	       mHandler.postDelayed(this,REFRESH_RATE);
 	   }
 	};
	 	
	private String timeToString (float time){
		
		String hours,minutes,seconds;
		long secs,mins,hrs;
		
		secs = (long)(time/1000);
		mins = (long)((time/1000)/60);
		hrs = (long)(((time/1000)/60)/60);
		
		secs = secs % 60;
		seconds=String.valueOf(secs);
    	if(secs == 0){
    		seconds = "00";
    	}else if(secs < 10 && secs > 0){
    		seconds = "0"+seconds;
    	}
    	
    	mins = mins % 60;
		minutes=String.valueOf(mins);
    	if(mins == 0){
    		minutes = "00";
    	}else if(mins < 10 && mins > 0){
    		minutes = "0"+minutes;
    	}
		
    	hours=String.valueOf(hrs);
    	if(hrs == 0){
    		hours = "00";
    	}else if(hrs < 10 && hrs > 0){
    		hours = "0"+hours;
    	}
    	
		return ("00".equals(hours)? (minutes + ":" + seconds) : (hours + ":" + minutes + ":" + seconds) );
	}
}
