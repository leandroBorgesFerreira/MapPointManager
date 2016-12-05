package br.com.simplepass.interfaces;



import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public interface IMapPointManager<T extends MapPoint> {
	
	void insertPoint(String company, T t);
	Optional<Map<Long, T>> pointsOfCompany(String company, Duration duration);
	Optional<T> pointsOfCompanyById(String company, long id, Duration duration);
	void clearOldPoints(Duration duration);
	
}
