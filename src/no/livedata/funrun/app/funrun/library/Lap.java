package no.livedata.funrun.app.funrun.library;

public class Lap {
	
	int id;
	int time;
	double dist;
	int act;
	
	
	public Lap() {
		id = 0;
		time = 0;
		dist = 0.0;
		act = 0;
		
	}
	
	public Lap(int id, int time, double dist, int act) {
		this.id = id;
		this.time = time;
		this.dist = dist;
		this.act = act;
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
	public double getDist() {
		return dist;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(double dist) {
		this.dist = dist;
	}

	/**
	 * @return the act
	 */
	public int getAct() {
		return act;
	}

	/**
	 * @param act the act to set
	 */
	public void setAct(int act) {
		this.act = act;
	}	

}
