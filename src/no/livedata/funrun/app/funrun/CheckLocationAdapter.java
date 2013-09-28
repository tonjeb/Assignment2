package no.livedata.funrun.app.funrun;
 
import java.util.ArrayList;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/*
 * CheckLocationAdapter
 * Custom adapter for managing positionlist
 */
public class CheckLocationAdapter extends BaseAdapter {
 
    private Activity activity; // the activity to use it in
    private ArrayList data; // the data to display
    private static LayoutInflater inflater=null; // layoutInflater to save inflated layout
    
    /*
     * CheckLocationAdapter (constructor)
     * constructor to set up the location adapter
     * @param Activity a the activity to use it in
     * @param ArrayList<Pos> d the data to put in adapter
     */
    public CheckLocationAdapter(Activity a, ArrayList d) {
        activity = a; // set activity
        data=d; // set data
        // inflate into the activity
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        return data.size(); // reurn count of data
    }
 
    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        return position;
    }
    
    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }
    
    /*
     * setData
     * replace the data in the adapter
     * @param ArrayList<Pos> d the data to use
     */
    public void setData(ArrayList d) {
    	data = d; // set data to the passed data
    }
    
    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView; // get the view
        /*if(convertView==null) // inflate it if its not set
            vi = inflater.inflate(R.layout.list_checklocation, null);
        
        // get ui elements
        TextView latlng = (TextView)vi.findViewById(R.id.latlng);
        TextView time = (TextView)vi.findViewById(R.id.time);
 
        Pos res = data.get(position); // get Pos object for position
        LatLng laln = res.getLatLng(); // get LatLng from Pos object
        // Set latlng to latitude and longitude
        latlng.setText(laln.latitude + ", " + laln.longitude);
        time.setText(res.getTime()); // set the time field
        */
        return vi;
    }
}