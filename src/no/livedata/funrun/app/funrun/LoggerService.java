package no.livedata.funrun.app.funrun;

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