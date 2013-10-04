package no.livedata.funrun.app.funrun.library;

public class Logg {
	
	int id;
	double lat;
	double lon;
	long time;
	double alt;
	double speed;
	int act;
	
	public Logg() {
		id = 0;
		lat = 0.0;
		lon = 0.0;
		time = 0;
		alt = 0.0;
		speed = 0.0;
		act = 0;
	}
	
	public Logg(int id, double lat, double lon, long time, double alt, double speed, int act) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.time = time;
		this.alt = alt;
		this.speed = speed;
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
	 * @return the alt
	 */
	public double getAlt() {
		return alt;
	}

	/**
	 * @param alt the alt to set
	 */
	public void setAlt(double alt) {
		this.alt = alt;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
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
