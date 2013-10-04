package no.livedata.funrun.app.funrun;

import java.util.ArrayList;

import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Act;
import no.livedata.funrun.app.funrun.library.Lap;
import no.livedata.funrun.app.funrun.library.ActivityAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;


public class ShowActivity {
	ListView activityView;
	ActivityAdapter adapter;
	ArrayList<Act> activityList = new ArrayList<Act>();
	int id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle recdData = getIntent().getExtras();
        id = recdData.getInt("id");
		activityView = (ListView)findViewById(R.id.activityList);
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		activityList = db.getActivities();
 		db.close();
 		adapter=new ActivityAdapter(); 
 		activityView.setAdapter(adapter);
	}


}
