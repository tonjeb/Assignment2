package no.livedata.funrun.app.funrun.library;

public class Lap {
	
	int id;
	long time;
	int dist;
	double lat;
	double lon;
	int act;
	
	
	public Lap() {
		id = 0;
		time = 0;
		dist = 0;
		lat = 0.0;
		lon = 0.0;
		act = 0;
		
	}
	
	public Lap(int id, long time, int dist, double lat, double lon, int act) {
		this.id = id;
		this.time = time;
		this.dist = dist;
		this.lat = lat;
		this.lon = lon;
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

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
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
