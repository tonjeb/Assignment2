package no.livedata.funrun.app.funrun;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Lap;
import no.livedata.funrun.app.funrun.library.Logg;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
/*
 * ShowLocation
 * Activity for showing users location on map
 * Uses fragments to embed map
 */
public class Map extends FragmentActivity implements LocationListener {
	
	private GoogleMap mMap; // to save map
	private LocationManager locationManager; // to save location
	private static final long MIN_TIME = 5000; // min time between positionupdate (milliseconds)
	private static final float MIN_DISTANCE = 0.1f; // min distance between positionupdate (meters)
	
	Button backButton;
	
	int activity;
	List<LatLng> routePoints;
	ArrayList<Logg> logList = new ArrayList<Logg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        // get data passed from ShowLocation
     	Bundle recdData = getIntent().getExtras();
        activity = recdData.getInt("act"); // set activity id
        
        backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener( new View.OnClickListener() {
			public void onClick (View v) {
				finish(); // end activity (go to last)
			}
		});
        
        setUpMapIfNeeded(); // initialize the map
        
        // initialize locationmanager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        if (activity != -1){
        	//addPoints();
        }
        
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menuitems to actionbar menu
		//getMenuInflater().inflate(R.menu.show_location, menu);
		return true;
	}
    
    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     * On activity resumes
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded(); // check if map needs update
        
        // clear excisting updates
        locationManager.removeUpdates(this); // stop updating position
        // start updating position again
        // requestLocationUpdates(networkprovider, mintime(milliseconds), mindistance(meter), listener)
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { // using the best provider posible
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }else{
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onPause()
     * on activity sent to background
     */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this); // stop updating position
    }
    
    @Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    	
	}
    
    private void addPoints() {
    	// loads points from db
    	DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		logList = db.getLog(activity);
 		db.close();
 		
 		// adds all points
 		for (Logg logg : logList) {
 			routePoints.add(new LatLng(logg.getLat(),logg.getLon()));
 		}
 		// creates polyline
    	Polyline route = mMap.addPolyline(new PolylineOptions()
    	  .width(5)
    	  .color(Color.RED)
    	  .geodesic(true));
    	// add points to map
    	route.setPoints(routePoints);
    }
    
    
    /*
     * setUpMapIfNeeded
     * setting up map if it is not set up before
     */
    private void setUpMapIfNeeded() {
        // Check if map is initiated before
        if (mMap == null) {
            // Get map from ui
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // if map is found
            if (mMap != null) {
                setUpMap(); // setup the map
            }
        }
    }
    
    /*
     * setUpMap
     * setting up map
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true); // set map to show users position
    }

    /*
     * when new position is available
     * (non-Javadoc)
     * @see android.location.LocationListener#onLocationChanged(android.location.Location)
     */
	@Override
	public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude()); // update latLng
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15); // create new position
        mMap.animateCamera(cameraUpdate); // goto new position
        Log.d("GPS", "new pos");
	}

	@Override
	public void onProviderDisabled(String arg0) {
		locationManager.removeUpdates(this); // stop updating position
    	// update location service on service update
    	if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {// using the best provider posible
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }else{
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		locationManager.removeUpdates(this); // stop updating position
    	// update location service on service update
    	if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {// using the best provider posible
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }else{
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }
	}
}
