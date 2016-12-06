package br.com.simplepass.interfaces;

import java.time.LocalDateTime;

public interface MapPoint {
	
	public long getId() ;	
	public double getLatitude();	
	public double getLongitude();
	public LocalDateTime getLastUpdate();
	
}
