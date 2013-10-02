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
public class Map extends FragmentActivity  {
	
	private GoogleMap mMap; // to save map
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        setUpMapIfNeeded(); // initialize the map
        
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
}
