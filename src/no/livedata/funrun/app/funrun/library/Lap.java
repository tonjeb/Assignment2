package no.livedata.funrun.app.funrun.library;

public class Lap {
	
	int lapKeyId;
	String lapKeyTime;
	int lapKeyAct;
	
	Lap() {
		int lapKeyId = 0;
		String lapKeyTime = "";
		String lapKeyAct = "";
		
	}
	
	Lap(int id, String time, String act) {
		int lapKeyId = id;
		String lapKeyTime = time;
		String lapKeyAct = act;
		
	}
	

}
