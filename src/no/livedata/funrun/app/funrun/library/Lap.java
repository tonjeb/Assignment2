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
	
	public int getLapKeyId() {
		return lapKeyId;
	}

	public void setLapKeyId(int lapKeyId) {
		this.lapKeyId = lapKeyId;
	}

	public String getLapKeyTime() {
		return lapKeyTime;
	}

	public void setLapKeyTime(String lapKeyTime) {
		this.lapKeyTime = lapKeyTime;
	}

	public int getLapKeyAct() {
		return lapKeyAct;
	}

	public void setLapKeyAct(int lapKeyAct) {
		this.lapKeyAct = lapKeyAct;
	}

	Lap(int id, String time, String act) {
		int lapKeyId = id;
		String lapKeyTime = time;
		String lapKeyAct = act;
		
	}
	

}
