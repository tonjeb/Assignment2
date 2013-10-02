package no.livedata.funrun.app.funrun.library;

import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoggAdapter extends BaseAdapter {
		
    private Activity activity; 
    private ArrayList<Logg> data; 
    private static LayoutInflater inflater=null; 

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
         vi = inflater.inflate(R.layout.list_checklocation, null);
	        
     // get ui elements
    TextView time = (TextView)vi.findViewById(R.id.time);
    TextView lat = (TextView)vi.findViewById(R.id.lat);
    TextView lon = (TextView)vi.findViewById(R.id.lon);
    TextView alt = (TextView)vi.findViewById(R.id.alt);
    TextView speed = (TextView)vi.findViewById(R.id.speed);

     Logg res = data.get(position); // get Laps object for position
     //LatLng laln = res.getLatLng(); // get LatLng from Pos object
     time.setText(res.getTime());  // set the time field
     lat.setText(res.getLat() + "");    // set the field lat
     lon.setText(res.getLon() + "");    // set the field lon
     alt.setText(res.getAlt() + "");    // set the field alt
     speed.setText(res.getSpeed() + ""); // set the field speed

     
			return vi;
		}
	

}
