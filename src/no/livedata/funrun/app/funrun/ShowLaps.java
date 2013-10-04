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

public class ShowLaps extends Activity {
	ListView lapView;
	LapAdapter adapter;
	ArrayList<Lap> lapList = new ArrayList<Lap>();
	int actId;
	
	Button backButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laplist);
		
		lapView = (ListView)findViewById(R.id.lapList);
		backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener( new View.OnClickListener() {
			public void onClick (View v) {
				finish();
			}
		});
        
		Bundle recdData = getIntent().getExtras();
        actId = recdData.getInt("act");
		
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		lapList = db.getLaps(actId);
 		db.close();
 		adapter = new LapAdapter(this, lapList); 
 		lapView.setAdapter(adapter);
	}
}

