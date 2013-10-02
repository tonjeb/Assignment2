package no.livedata.funrun.app.funrun;

import java.lang.reflect.Field;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
/*
 * ShowLocation
 * Activity for showing users location on map, and send it by email
 * Uses fragments to embed map
 */
public class Map extends FragmentActivity implements android.location.LocationListener  {
	
	private GoogleMap mMap; // to save map
	private LocationManager locationManager; // to save location
	private static final long MIN_TIME = 400; // min time between positionupdate (milliseconds)
	private static final float MIN_DISTANCE = 10; // min distance between positionupdate (meters)
	private LatLng latLng = new LatLng(0, 0); // set latLng to 0 position
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        setUpMapIfNeeded(); // initialize the map
        
        
        // initialize locationmanager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        
        // Add menuicon to actionbar
        try {
            ViewConfiguration config = ViewConfiguration.get(this); // get view config
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey"); // get menuicon fireld
            if(menuKeyField != null) { // if menufield avible
                menuKeyField.setAccessible(true); // activate menuicon
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // start updating position again
        // requestLocationUpdates(networkprovider, mintime(milliseconds), mindistance(meter), listener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
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
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * on menuitem selected
     */
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()) { // switch on selected element
			case R.id.menu_net: // if the show net page selected
				Intent chkLoc = new Intent(getApplicationContext(), CheckLocation.class); // new intent for CheckLocation activity
	        	chkLoc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	// set extra parameters (latitude and longitude)
	        	chkLoc.putExtra("lat",latLng.latitude + "");
	        	chkLoc.putExtra("lng",latLng.longitude + "");
	        	startActivity(chkLoc); // start the activity
				break;
			default:
				break;
		}	*/
		return true;
	}
    
    /*
     * (non-Javadoc)
     * @see android.location.LocationListener#onLocationChanged(android.location.Location)¨
     */
    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude()); // update latLng
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15); // create new position
        mMap.animateCamera(cameraUpdate); // goto new position
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
