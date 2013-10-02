package no.livedata.funrun.app.funrun.library;

public class Activity {
	
	int start;
	int id;
	int time;
	Double dist;

	public Activity() {
		id = 0;
		start = "";
		time = 0;
		dist = 0.0;
		
	}
	
	public Activity(int id, int start, int time, Double dist) {
		this.id = id;
		this.start = start;
		this.time = time;
		this.dist = dist;
		
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the dist
	 */
	public Double getDist() {
		return dist;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(Double dist) {
		this.dist = dist;
	}
	
}
