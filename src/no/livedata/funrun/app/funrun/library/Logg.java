package no.livedata.funrun.app.funrun.library;

	public class Logg {
		
		int logKeyId;
		double logKeyLat;
		double logKeyLon;
		String logKeyTime;
		double logKeyAlt;
		int logKeyAct;
		
		public int getLogKeyId() {
			return logKeyId;
		}

		public void setLogKeyId(int logKeyId) {
			this.logKeyId = logKeyId;
		}

		public double getLogKeyLat() {
			return logKeyLat;
		}

		public void setLogKeyLat(double logKeyLat) {
			this.logKeyLat = logKeyLat;
		}

		public double getLogKeyLon() {
			return logKeyLon;
		}

		public void setLogKeyLon(double logKeyLon) {
			this.logKeyLon = logKeyLon;
		}

		public String getLogKeyTime() {
			return logKeyTime;
		}

		public void setLogKeyTime(String logKeyTime) {
			this.logKeyTime = logKeyTime;
		}

		public double getLogKeyAlt() {
			return logKeyAlt;
		}

		public void setLogKeyAlt(double logKeyAlt) {
			this.logKeyAlt = logKeyAlt;
		}

		public int getLogKeyAct() {
			return logKeyAct;
		}

		public void setLogKeyAct(int logKeyAct) {
			this.logKeyAct = logKeyAct;
		}

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
