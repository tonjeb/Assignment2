package no.livedata.funrun.app.funrun;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
 
public class GPSLogger extends Service implements LocationListener {
	
	// save current context
    private Context context;
 
    // flag if GPS enabled
    boolean haveGPS = false;
 
    // flag if Netwwork enabled
    boolean haveNetwork = false;
 
    Location location; // location
    double lat; // latitude
    double lon; // longitude
    
    int activity;
 
    // Minimum position change for update
    private static float MIN_DISTANCE = 0.1f; // 0.1 meters, to update each time
 
    // Minimum time between updates
    private static long MIN_TIME = 1000 * 5; // 5 seconds, to get update every 5 seconds
 
    // Location Manager to controll location
    protected LocationManager locationManager;
 
    @Override
    public void onStart(Intent intent, int startId) {
    	
        Bundle extras = intent.getExtras(); 
		if(extras != null) {
		    activity = (int) Integer.parseInt(extras.get("ACT").toString());
		    startLocationListener();
		    Log.d("GPS","Logger started");
		}

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
        	showSettingsAlert();
        }
    }

    @Override
    public void onDestroy() {
        if(locationManager != null){
            locationManager.removeUpdates(GPSLogger.this);
        }      
    }
     
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS is settings");
  
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
 
    @Override
    public void onLocationChanged(Location location) {
    	Log.d("GPS","Location logged: "+location.getLatitude() + " " + location.getLongitude());
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
}