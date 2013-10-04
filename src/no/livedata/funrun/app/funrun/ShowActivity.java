package no.livedata.funrun.app.funrun;

import java.util.ArrayList;

import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Act;
import no.livedata.funrun.app.funrun.library.Lap;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/*
 * show activities
 */
public class ShowActivity extends Activity {
	ListView activityView; // activity list
	ActivityAdapter adapter; // activity adapter
	ArrayList<Act> activityList = new ArrayList<Act>(); // activitylist
	
	Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitylist);
		
		activityView = (ListView)findViewById(R.id.activityList);
		
		backButton = (Button) findViewById(R.id.backButton);
        
		// exit activity
        backButton.setOnClickListener( new View.OnClickListener() {
			public void onClick (View v) {
				finish();
			}
		});
        
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		activityList = db.getActivities(); // get activities
 		db.close();
 		
 		// add activities to adapter
 		adapter = new ActivityAdapter(this, activityList); 
 		// add adapter to list
 		activityView.setAdapter(adapter);
 		
 		// Click event for single list row
 		activityView.setOnItemClickListener(new OnItemClickListener() {

			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent lapInt = new Intent(getApplicationContext(), ShowLaps.class);
				lapInt.putExtra("act",activityList.get(position).getId());

				startActivity(lapInt);		
			}
		});
	}

}
