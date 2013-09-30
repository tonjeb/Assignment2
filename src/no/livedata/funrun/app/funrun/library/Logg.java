package no.livedata.funrun.app.funrun.library;

	public class Logg {
		
		int logKeyId;
		double logKeyLat;
		double logKeyLon;
		String logKeyTime;
		double logKeyAlt;
		int logKeyAct;
		
		Logg() {
			logKeyId = 0;
			logKeyLat = 0.0;
			logKeyLon = 0.0;
			logKeyTime = "";
		logKeyAlt = 0.0;
		logKeyAct = 0;
	}
	
	Logg(int id, double lat, double lon, String time, double alt, int act) {
		logKeyId = id;
		logKeyLat = lat;
		logKeyLon = lon;
		logKeyTime = time;
		logKeyAlt = alt;
		logKeyAct = act;
	}
		

}
