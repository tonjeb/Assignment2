package no.livedata.funrun.app.funrun;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Main extends Activity {
	
	LoggingService ls;
	
	Button StartButton;
	Button LapButton;
	TextView TimeView;
	
	// Timer
	private Handler mHandler = new Handler();
	private long startTime;
	private long stopTime;
	private long elapsedTime;
	private final int REFRESH_RATE = 100;
	private String hours,minutes,seconds;
	private long secs,mins,hrs;
	private boolean stopped = false;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ls = new LoggingService(getApplicationContext());
		Log.d("LS", "ls called");
		
		StartButton = (Button) findViewById(R.id.StartButton);
		LapButton = (Button) findViewById(R.id.LapButton);
		
		TimeView = (TextView) findViewById(R.id.TimeView);
	
		StartButton.setTag(1);
		StartButton.setText("Start");//husk å sette string
		StartButton.setOnClickListener( new View.OnClickListener() {
	
			public void onClick (View v) {
				final int status =(Integer) v.getTag();
				if(status == 1) { // START
					startTime();
					ls.startService();
					
				    //take the time
					LapButton.setText("Lap");//husk å sette string
					LapButton.setEnabled(true);
				    StartButton.setText("Stop");//husk å sette string
				    v.setTag(0); //pause
				} else { // STOP
					stopTime();
					ls.stopService();
					
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
	        .setMessage("Ved å gå ut av appen avsluttes tellingen. Vil du gå ut?")
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
