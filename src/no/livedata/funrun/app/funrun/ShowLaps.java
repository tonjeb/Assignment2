package no.livedata.funrun.app.funrun;

import java.util.ArrayList;

import no.livedata.funrun.app.funrun.library.Act;
import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Lap;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/*
 * Show all laps for activity
 */
public class ShowLaps extends Activity {
	ListView lapView; // the list
	LapAdapter adapter; // lap adapter
	ArrayList<Lap> lapList = new ArrayList<Lap>(); // list of lap objects to display
	int actId; // activity id
	
	Button backButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laplist);
		
		lapView = (ListView)findViewById(R.id.lapList);
		backButton = (Button) findViewById(R.id.backButton);
        
		// exit activity
        backButton.setOnClickListener( new View.OnClickListener() {
			public void onClick (View v) {
				finish();
			}
		});
        
        // get activity id
		Bundle recdData = getIntent().getExtras();
        actId = recdData.getInt("act");
		
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		lapList = db.getLaps(actId); // get laps
 		db.close();
 		
 		// insert laps into adapter
 		adapter = new LapAdapter(this, lapList);
 		// set adapter for list
 		lapView.setAdapter(adapter);
	}
}

