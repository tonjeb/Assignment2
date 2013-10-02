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
	
	// Timer
	Handler mHandler = new Handler();
	long startTime;
	long stopTime;
	long elapsedTime;
	final int REFRESH_RATE = 100;
	String hours,minutes,seconds;
	long secs,mins,hrs;
	boolean stopped = false;
	
	int distance = 0;
	int activity = -1;
	int time = 0;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
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
				    v.setTag(0); //pause

				} else { // STOP
					stopTime();
					stopService(serviceIntent);
					

				    startButton.setText(getResources().getString(R.string.start));
				    lapButton.setText(getResources().getString(R.string.reset));

				    v.setTag(1); //pause
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
				    v.setTag(0); //pause
				} else { // LAP
					newLap();
				    v.setTag(1); //pause
				} 
			}

			private void setEnabled(boolean b) {
				// TODO Auto-generated method stub
				
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
	            finish();    
	        }

	    })
	    .setNegativeButton(getResources().getString(R.string.no), null)
	    .show();
	}
	
	@Override
	protected void onStop() {
		unregisterReceiver(logReceiver);
		super.onStop();
	}
	
	private void newActivity() {
		if (activity == -1) { // create new activity if no activity active
			// connect to db
	 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
	 		activity = db.insertActivity(new Act(
	 							0,
	 							(int)System.currentTimeMillis()/1000,
	 							0,
	 							0
	 						));
	 		db.close();
		}
	}
	
	private void closeActivity() {
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		db.updateActivity(activity,time,distance); // update current activity with time and distance
 		db.close();
 		activity = -1;
	}
	
	private void newLap() {
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		db.insertLap(new Lap ( // insert new lap to db
 							0,
 							time,
 							distance,
 							activity
 						));
 		db.close();
 		lapView.setText(timeToString(time));
	}
	
	private class LoggerReceiver extends BroadcastReceiver{
		 
		 @Override
		 public void onReceive(Context con, Intent in) {

		  long dist = in.getLongExtra("DIST", -1);
		  double speed = in.getDoubleExtra("SPEED", -1);
		  
		  if (dist != -1) {
			  Toast.makeText(Main.this,
			    "Triggered by Service!\n"
			    + "Data passed: " + String.valueOf(dist),
			    Toast.LENGTH_LONG).show();
			  distance = (int) dist;
			  distView.setText((double)dist/1000+"");
		  }
		  
		  if (speed != -1) {
			  //DistView.setText((double)dist/1000+"");
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
    	timeView.setText("00:00:00");	
    }
    
    private Runnable startTimer = new Runnable() {
 	   public void run() {
 		   elapsedTime = System.currentTimeMillis() - startTime;
 		   timeView.setText(timeToString(elapsedTime));
 		   time = (int) elapsedTime/1000;
 	       mHandler.postDelayed(this,REFRESH_RATE);
 	   }
 	};
	 	
	private String timeToString (float time){
		
		secs = (long)(time/1000);
		mins = (long)((time/1000)/60);
		hrs = (long)(((time/1000)/60)/60);
		
		secs = secs % 60;
		seconds=String.valueOf(secs);
    	if(secs == 0){
    		seconds = "00";
    	}else if(secs <10 && secs > 0){
    		seconds = "0"+seconds;
    	}
    	
    	mins = mins % 60;
		minutes=String.valueOf(mins);
    	if(mins == 0){
    		minutes = "00";
    	}else if(mins <10 && mins > 0){
    		minutes = "0"+minutes;
    	}
		
    	hours=String.valueOf(hrs);
    	if(hrs == 0){
    		hours = "00";
    	}else if(hrs <10 && hrs > 0){
    		hours = "0"+hours;
    	}
    	
		return ("00".equals(hours)? (minutes + ":" + seconds) : (hours + ":" + minutes + ":" + seconds) );
	}
}
