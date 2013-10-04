package no.livedata.funrun.app.funrun.library;

public class Act {
	
	int id;
	long start;
	long time;
	int dist;

	public Act() {
		id = 0;
		start = 0;
		time = 0;
		dist = 0;
	}
	
	public Act(int id, long start, long time, int dist) {
		this.id = id;
		this.start = start;
		this.time = time;
		this.dist = dist;
	}

	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(long start) {
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
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the dist
	 */
	public int getDist() {
		return dist;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}
	
}
