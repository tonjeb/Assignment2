package no.livedata.funrun.app.funrun;

import java.util.Locale;


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
	
	Button StartButton;
	Button LapButton;
	TextView TimeView;
	TextView DistView;
	
	// Timer
	Handler mHandler = new Handler();
	long startTime;
	long stopTime;
	long elapsedTime;
	final int REFRESH_RATE = 100;
	String hours,minutes,seconds;
	long secs,mins,hrs;
	boolean stopped = false;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		logReceiver = new LoggerReceiver();
	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(LoggerService.UPDATE_UI);
	    registerReceiver(logReceiver, intentFilter);
		
		serviceIntent = new Intent(Main.this, LoggerService.class); 
		
		StartButton = (Button) findViewById(R.id.StartButton);
		LapButton = (Button) findViewById(R.id.LapButton);
		
		TimeView = (TextView) findViewById(R.id.TimeView);
		DistView = (TextView) findViewById(R.id.DistView);
	
		StartButton.setTag(1);
		StartButton.setText("Start");//husk å sette string
		StartButton.setOnClickListener( new View.OnClickListener() {
	
			public void onClick (View v) {
				final int status =(Integer) v.getTag();
				if(status == 1) { // START
					startTime();
					serviceIntent.putExtra("ACT", "1");
					startService(serviceIntent);
					Log.d("SE","Service started");
					
				    //take the time
					LapButton.setText("Lap");//husk å sette string
					LapButton.setEnabled(true);
				    StartButton.setText("Stop");//husk å sette string
				    v.setTag(0); //pause
				} else { // STOP
					stopTime();
					stopService(serviceIntent);
					
				    StartButton.setText("Start");//husk å sette string
				    LapButton.setText("Reset");//husk å sette string
				    v.setTag(1); //pause
				} 
			}
		});
		
		LapButton.setTag(1);
		LapButton.setText("Lap");//husk å sette string
		LapButton.setEnabled(false);
		LapButton.setOnClickListener(new View.OnClickListener() {
	

				public void onClick (View v) {
					final int status =(Integer) v.getTag();
					if(status == 1) { // RESET
						resetTime();
						
						//reset time for lap and save the time
					    v.setTag(0); //pause
					} else { // LAP
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
	        .setTitle("Avslutte") // TODO: Bruke stringer
	        .setMessage("Ved å gå ut av appen avsluttes aktiviteten. Vil du virkelig gå ut?")
	        .setPositiveButton("Ja", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("Nei", null)
	    .show();
	}
	
	@Override
	protected void onStop() {
		unregisterReceiver(logReceiver);
		super.onStop();
	}
	
	private class LoggerReceiver extends BroadcastReceiver{
		 
		 @Override
		 public void onReceive(Context arg0, Intent arg1) {

		  long dist = arg1.getLongExtra("DIST", -1);
		  
		  if (dist != -1) {
			  Toast.makeText(Main.this,
			    "Triggered by Service!\n"
			    + "Data passed: " + String.valueOf(dist),
			    Toast.LENGTH_LONG).show();
			  DistView.setText((double)dist/1000+"");
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
    	TimeView.setText("00:00:00");	
    }
    
    private Runnable startTimer = new Runnable() {
	 	   public void run() {
	 		   elapsedTime = System.currentTimeMillis() - startTime;
	 		   updateTimer(elapsedTime);
	 	       mHandler.postDelayed(this,REFRESH_RATE);
	 	   }
	 	};
	 	
	private void updateTimer (float time){
		secs = (long)(time/1000);
		mins = (long)((time/1000)/60);
		hrs = (long)(((time/1000)/60)/60);
		
		/* Convert the seconds to String 
		 * and format to ensure it has
		 * a leading zero when required
		 */
		secs = secs % 60;
		seconds=String.valueOf(secs);
    	if(secs == 0){
    		seconds = "00";
    	}
    	if(secs <10 && secs > 0){
    		seconds = "0"+seconds;
    	}
    	
		/* Convert the minutes to String and format the String */
    	
    	mins = mins % 60;
		minutes=String.valueOf(mins);
    	if(mins == 0){
    		minutes = "00";
    	}
    	if(mins <10 && mins > 0){
    		minutes = "0"+minutes;
    	}
		
    	/* Convert the hours to String and format the String */
    	
    	hours=String.valueOf(hrs);
    	if(hrs == 0){
    		hours = "00";
    	}
    	if(hrs <10 && hrs > 0){
    		hours = "0"+hours;
    	}
    	
		/* Setting the timer text to the elapsed time */
		TimeView.setText(hours + ":" + minutes + ":" + seconds);
	}
}
