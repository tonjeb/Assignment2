package no.livedata.funrun.app.funrun.library;

import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LapAdapter extends BaseAdapter {
	
    private Activity activity; 
    private ArrayList<Lap> data; 
    private static LayoutInflater inflater=null; 
    
    public LapAdapter(Activity a, ArrayList<Lap> d){
    	
    	activity = a;
    	data = d;    
    	inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*public View getView(int position, View convertView, ViewGroup parent) {
		 View vi=convertView; // get the view
	        if(convertView==null) // inflate it if its not set
	            vi = inflater.inflate(R.layout.laplistitem, null);
	        
	        // get ui elements
	       TextView time = (TextView)vi.findViewById(R.id.time);
	       TextView dist = (TextView)vi.findViewById(R.id.dist);
	 
	        Lap res = data.get(position); // get Laps object for position
	        //LatLng laln = res.getLatLng(); // get LatLng from Pos object
	        time.setText(res.getTime()); // set the time field
	        dist.setText(res.getDist()); // set the field dists
	        
	        
			return vi;
		}*/
}
