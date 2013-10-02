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
    private static float MIN_DISTANCE = 0.1f; // 0.1 meters, to update each time
 
    // Minimum time between updates
    private static long MIN_TIME = 1000 * 5; // 5 seconds, to get update every 5 seconds
 
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
	int activity;
	
	public LoggerService() {
	}
	
	@Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startId) {
    	context = this;
        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
        Bundle extras = intent.getExtras(); 
		if(extras != null) {
		    activity = (int) Integer.parseInt(extras.get("ACT").toString());
		    Log.d("GPS","Logger started");
		    Toast.makeText(this, " Act: " + activity, Toast.LENGTH_LONG).show();
		    startLocationListener();
		}

    }
    

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        if(locationManager != null){
            locationManager.removeUpdates(LoggerService.this);
        }
    }
    
    @Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
    
    private void startLocationListener() {
    	locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    	 
        // check if GPS is available
        haveGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // checking network status
        haveNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
    	// if we have gps enabled, get location from it
        if (haveGPS) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE, this);
        }else if (haveNetwork) { // else try network to get location
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
		Log.d("GPS","Location logged: "+location.getLatitude() + " " + location.getLongitude());
		location.getAltitude();
		location.getSpeed();
		
		if(lastLat != 0 && lastLon != 0) { 
	       Location.distanceBetween(lastLat,lastLon,location.getLatitude(),location.getLongitude(),dist);
	       distance+=(long)dist[0];
	    }

	    lastLat=location.getLatitude();
	    lastLon=location.getLongitude();
	    
	    Log.d("GPS","Distance: " + (double)distance/1000);
	    
	    // connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		db.insertLogg(new Logg (
 							0,
 							location.getLatitude(),
 							location.getLongitude(),
 							(int)System.currentTimeMillis()/1000,
 							location.getAltitude(),
 							location.getSpeed(),
 							activity
 						));
 		db.close();
	    
	    // notify ui about change
	    Intent intent = new Intent();
	    intent.setAction(UPDATE_UI);
	    intent.putExtra("DIST", distance);
	    sendBroadcast(intent);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}