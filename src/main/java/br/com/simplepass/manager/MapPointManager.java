package br.com.simplepass.manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.simplepass.interfaces.IMapPointManager;
import br.com.simplepass.interfaces.MapPoint;

public class MapPointManager<T extends MapPoint> implements IMapPointManager<T>{
	
	private Map<String, Map<Long, T>> mPointsOfCompanies;
	
	public MapPointManager() {	
		this.mPointsOfCompanies = new HashMap<>();	
	}

	public Optional<Map<Long, T>> pointsOfCompany(String company, Duration duration) {
		Optional<Map<Long, T>> companyMap = mPointsOfCompanies
				.entrySet()
				.stream()
				.filter(m -> m.getKey().equals(company))
				.map(m -> m.getValue())			
				.findFirst();
		
		if(companyMap.isPresent()){
			Map<Long, T> points = companyMap
				.get()
				.entrySet()
				.stream()
				//.peek(System.out::println)
				.filter(e -> isNewEnougth(e.getValue().getLastUpdate(), duration))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
			
			return Optional.of(points);			
		} else{
			return Optional.empty();
		}
	}
	

	@Override
	public Optional<T> pointsOfCompanyById(String company, long id, Duration duration) {
		Optional<Map<Long, T>> companyPoints = pointsOfCompany(company, duration);
		
		if(companyPoints.isPresent()){
			return companyPoints.get()
				.entrySet()
				.stream()
				.filter(e -> e.getValue().getId() == id)
				.map(e -> e.getValue())
				.findFirst();
		}
		
		return Optional.empty();
	}

	public void clearOldPoints(Duration duration) {
		mPointsOfCompanies
			.entrySet()
			.stream()		
			.forEach(e -> e.getValue().entrySet().removeIf(e2 -> !isNewEnougth(e2.getValue().getLastUpdate(), duration)));
		
	}

	public void insertPoint(String company, T t) {
		if(mPointsOfCompanies.containsKey(company)){
			mPointsOfCompanies.get(company).put(t.getId(), t);
		} else{				
			Map<Long, T> pointsMap = new HashMap<>();
			pointsMap.put(t.getId(), t);
			
			mPointsOfCompanies.put(company, pointsMap);
		}
		
	}
	
	private boolean isNewEnougth(LocalDateTime localDateTime, Duration duration){
		Duration oldDuration = Duration.between(localDateTime, LocalDateTime.now());
		return oldDuration.compareTo(duration) < 0;
	}
	

}
