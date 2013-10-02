package no.livedata.funrun.app.funrun;

import java.util.ArrayList;

import no.livedata.funrun.app.funrun.library.Act;
import no.livedata.funrun.app.funrun.library.DatabaseHandler;
import no.livedata.funrun.app.funrun.library.Lap;
import no.livedata.funrun.app.funrun.library.LapAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowLaps extends Activity {
	ListView lapView;
	LapAdapter adapter;
	ArrayList<Lap> lapList = new ArrayList<Lap>();
	int actId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle recdData = getIntent().getExtras();
        actId = Integer.parseInt(recdData.getString("act"));
		lapView = (ListView)findViewById(R.id.lapList);
		// connect to db
 		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 		lapList = db.getLaps(actId);
 		db.close();
 		adapter=new LapAdapter(this, lapList); 
 		lapView.setAdapter(adapter);
	}
}

