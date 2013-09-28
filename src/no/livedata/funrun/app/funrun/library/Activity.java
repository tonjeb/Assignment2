package no.livedata.funrun.app.funrun.library;

public class Activity {
	
	String actKeyStart;
	int actKeyId;
	String actKeyTime;

	Activity() {
		actKeyId = 0;
		actKeyStart = "";
		actKeyTime = "";
		
	}
	
	Activity(int id, String start, String time) {
		actKeyId = id;
		actKeyStart = start;
		actKeyTime = time;
		
	}
	
}
