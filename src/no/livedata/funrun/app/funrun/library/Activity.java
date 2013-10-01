package no.livedata.funrun.app.funrun.library;

public class Activity {
	
	String actKeyStart;
	int actKeyId;
	String actKeyTime;
	
	public String getActKeyStart() {
		return actKeyStart;
	}

	public void setActKeyStart(String actKeyStart) {
		this.actKeyStart = actKeyStart;
	}

	public int getActKeyId() {
		return actKeyId;
	}

	public void setActKeyId(int actKeyId) {
		this.actKeyId = actKeyId;
	}

	public String getActKeyTime() {
		return actKeyTime;
	}

	public void setActKeyTime(String actKeyTime) {
		this.actKeyTime = actKeyTime;
	}


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
