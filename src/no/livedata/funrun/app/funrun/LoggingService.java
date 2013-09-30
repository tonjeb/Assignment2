package no.livedata.funrun.app.funrun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LoggingService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL = 1000 * 10; // 10 seconds
    
    private Context context;
    GPSTracker gps;
 
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    public LoggingService (Context con) {
    	Log.d("LS","const");
    	context = con;
    	gps = new GPSTracker(con);
    	if(!gps.canGetLocation()){
            gps.showSettingsAlert();
        }else{
        	gps.getLocation();
        }
    }
    
    public void stopService() {
    	if(mTimer != null) {
    		mTimer.cancel();
    		mTimer = null;
    	}
    	gps.stopUsingGPS();
    }
    
    public void startService() {
    	onCreate();
    }
 
    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
        Log.d("LS","start");
    }
 
    class TimeDisplayTimerTask extends TimerTask {
 
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
 
                @Override
                public void run() {
                    // display toast
                    Toast.makeText(context, getDateTime() + "|" + gps.getLatitude() + "," + gps.getLongitude() , Toast.LENGTH_SHORT).show();
                }
 
            });
        }
 
        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
 
    }
}