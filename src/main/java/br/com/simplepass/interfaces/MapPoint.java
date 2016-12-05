package br.com.simplepass.interfaces;

import java.time.LocalTime;

public interface MapPoint {
	
	public long getId() ;	
	public double getLatitude();	
	public double getLongitude();
	public LocalTime getLastUpdate();
	
}
