package no.livedata.funrun.app.funrun;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity {
	
	
	Button StartButton;
	Button LapButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		StartButton = (Button) findViewById(R.id.StartButton);
		LapButton = (Button) findViewById(R.id.LapButton);

	
		StartButton.setTag(1);
		StartButton.setText("Start");//husk å sette string
		StartButton.setOnClickListener( new View.OnClickListener() {
	
			public void onClick (View v) {
				final int status =(Integer) v.getTag();
				if(status == 1) {
				    //take the time
					LapButton.setText("Lap");//husk å sette string
					LapButton.setEnabled(true);
				    StartButton.setText("Stop");//husk å sette string
				    v.setTag(0); //pause
				} else {
				    StartButton.setText("Start");//husk å sette string
				    LapButton.setText("Reset");//husk å sette string
				    LapButton.setEnabled(true);
				    v.setTag(1); //pause
				} 
			}
		});
		
		LapButton.setTag(1);
		LapButton.setText("Lap");//husk å sette string
		LapButton.setEnabled(false);
		LapButton.setOnClickListener(new View.OnClickListener() {
	

				public void onClick (View v) {
					final int status =(Integer) v.getTag();
					if(status == 1) {
						//reset time for lap and save the time
					    v.setTag(0); //pause
					} else {
					    v.setTag(1); //pause
					} 
			}

				private void setEnabled(boolean b) {
					// TODO Auto-generated method stub
					
				}
		});
	}
	


	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	};
}
