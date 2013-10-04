package no.livedata.funrun.app.funrun;

import java.util.ArrayList;

import no.livedata.funrun.app.funrun.library.Logg;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoggAdapter extends BaseAdapter {
		
    private Activity activity; 
    private ArrayList<Logg> data; 
    private static LayoutInflater inflater=null; 
    
    /*
     * CheckLocationAdapter (constructor)
     * constructor to set up the location adapter
     * @param Activity a the activity to use it in
     * @param ArrayList<Pos> d the data to put in adapter
     */
    public LoggAdapter(Activity a, ArrayList<Logg> d) {
        activity = a; // set activity
        data=d; // set data
        // inflate into the activity
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	public int getCount() {
	      return data.size(); // return count of data
	}

	public Object getItem(int position) {
		return position;
	}
	
    public long getItemId(int position) {
        return position;
    }

	public View getView(int position, View convertView, ViewGroup parent) {
		 View vi=convertView; // get the view	        
		 if(convertView==null) // inflate it if its not set
         vi = inflater.inflate(R.layout.logglistitem, null);
	        
	     // get ui elements
	     TextView time = (TextView)vi.findViewById(R.id.time);
	     TextView lat = (TextView)vi.findViewById(R.id.lat);
	     TextView lon = (TextView)vi.findViewById(R.id.lon);
	     TextView alt = (TextView)vi.findViewById(R.id.alt);
	     TextView speed = (TextView)vi.findViewById(R.id.speed);
	
	     Logg res = data.get(position); // get Laps object for position
	     //LatLng laln = res.getLatLng(); // get LatLng from Pos object
	     time.setText(timeToString(res.getTime()));  // set the time field
	     lat.setText(res.getLat() + "");    // set the field lat
	     lon.setText(res.getLon() + "");    // set the field lon
	     alt.setText(res.getAlt() + "");    // set the field alt
	     speed.setText(res.getSpeed() + ""); // set the field speed
	
	     
		return vi;
	}
	
	private String timeToString (float time){
		
		String hours,minutes,seconds;
		long secs,mins,hrs;
		
		secs = (long)(time/1000);
		mins = (long)((time/1000)/60);
		hrs = (long)(((time/1000)/60)/60);
		
		secs = secs % 60;
		seconds=String.valueOf(secs);
    	if(secs == 0){
    		seconds = "00";
    	}else if(secs < 10 && secs > 0){
    		seconds = "0"+seconds;
    	}
    	
    	mins = mins % 60;
		minutes=String.valueOf(mins);
    	if(mins == 0){
    		minutes = "00";
    	}else if(mins < 10 && mins > 0){
    		minutes = "0"+minutes;
    	}
		
    	hours=String.valueOf(hrs);
    	if(hrs == 0){
    		hours = "00";
    	}else if(hrs < 10 && hrs > 0){
    		hours = "0"+hours;
    	}
    	
		return ("00".equals(hours)? (minutes + ":" + seconds) : (hours + ":" + minutes + ":" + seconds) );
	}

}
