package domain;

import java.time.LocalTime;

import br.com.simplepass.interfaces.MapPoint;

public class Car implements MapPoint{
	long id;
	double latitude;
	double longitude;
	LocalTime lastUpdate;
	
	
	
	public Car(long id, double latitude, double longitude, LocalTime lastUpdate) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.lastUpdate = lastUpdate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public LocalTime getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(LocalTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", lastUpdate=" + lastUpdate
				+ "]";
	}
	

}
