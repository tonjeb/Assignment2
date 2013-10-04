package no.livedata.funrun.app.funrun;

import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Logg;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LoggerService extends Service implements LocationListener {
	
	// save current context
    private Context context;
 
    // flag if GPS enabled
    boolean haveGPS = false;
 
    // flag if Netwwork enabled
    boolean haveNetwork = false;
    
    // Minimum position change for update
    private static float MIN_DISTANCE = 0; // 0.1 meters, to update each time
 
    // Minimum time between updates
    private static long MIN_TIME = 0; // 5 seconds, to get update every 5 seconds
 
    // Location Manager to controll location
    protected LocationManager locationManager;
    
    // helpers to calculate distance
    double lastLat = 0;
    double lastLon = 0;
    
    float dist[] = new float[1];
    
    // current distance
    long distance = 0; // in km
    
    // intent action to update ui
    final static String UPDATE_UI = "UPDATE_UI";
    
    // the id of the current activity
	int activity = 0;
	
	long lastTime = 0;
	
	public LoggerService() {
	}
	
	@Override
    public void onCreate() {
        
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	context = this;
        if (intent.getExtras() != null) {
	        Bundle extras = intent.getExtras(); 
			if(extras != null) {
			    activity = (int) Integer.parseInt(extras.get("ACT").toString());
			    Log.d("GPS","Logger started");
			}
        }
		startLocationListener();
		
        return START_STICKY; // run until stopped
    }
    

    @Override
    public void onDestroy() {
        if(locationManager != null){
            locationManager.removeUpdates(LoggerService.this);
        }
    }
    
    @Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("");
	}
    
    private void startLocationListener() {
    	if(locationManager != null){
            locationManager.removeUpdates(LoggerService.this);
        }
    	
    	locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    	 
        // check if GPS is available
        haveGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // checking network status
        haveNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
    	// if we have gps enabled, get location from it
        if (haveGPS) { // TODO: Bare bruke GPS?
        	Log.d("GPS","on");
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE, this);
        }else if (haveNetwork) { // else try network to get location
        	Log.d("GPS","off");
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE, this);
        }else {
        	//showSettingsAlert();
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		location.getAltitude();
		location.getSpeed();
		
		// calculate distance
		if(lastLat != 0 && lastLon != 0) { 
	       Location.distanceBetween(lastLat,lastLon,location.getLatitude(),location.getLongitude(),dist);
	       distance+=(long)dist[0];
	    }

	    lastLat=location.getLatitude();
	    lastLon=location.getLongitude();
	    
	    
	    // send broadcast
	    if (activity != 0 && (lastTime + 5000) < System.currentTimeMillis()) {
	    	
		    // connect to db
	 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
	 		db.insertLogg(new Logg (
	 							0,
	 							location.getLatitude(),
	 							location.getLongitude(),
	 							(long)System.currentTimeMillis(),
	 							location.getAltitude(),
	 							location.getSpeed(),
	 							activity
	 						));
	 		db.close();
		    
		    // notify ui about change
		    Intent intent = new Intent();
		    intent.setAction(UPDATE_UI);
		    intent.putExtra("DIST", distance);
		    intent.putExtra("SPEED", location.getSpeed());
		    intent.putExtra("LAT", location.getLatitude());
		    intent.putExtra("LON", location.getLongitude());
		    sendBroadcast(intent);
		    lastTime = (long) System.currentTimeMillis();
	    }
	}

	@Override
	public void onProviderDisabled(String arg0) {
		startLocationListener(); // update location
	}

	@Override
	public void onProviderEnabled(String arg0) {
		startLocationListener(); // update location
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}
}
