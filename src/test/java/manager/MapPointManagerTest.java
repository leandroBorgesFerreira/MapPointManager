package manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.simplepass.manager.MapPointManager;
import domain.Car;

public class MapPointManagerTest {	
	String mCompany;
	
	@Before
	public void setup(){
		mCompany = "Simple Pass";
	}
	
	@Test
	public void nullTest(){
		
		MapPointManager<Car> manager = new MapPointManager<>();
		
		Optional<Car> carOptional = manager.pointsOfCompanyById(
				mCompany, 
				1L, 
				ChronoUnit.FOREVER.getDuration());

		Assert.assertFalse(carOptional.isPresent());
	}
	
	@Test
	public void getPointOfCompanyByIdTest(){
		long id = 55L;
		Car car = new Car(id, -19.35, -41.0, LocalDateTime.now());
		
		MapPointManager<Car> manager = new MapPointManager<>();
		
		manager.insertPoint(mCompany, car);
		
		Car carFromManager = manager.pointsOfCompanyById(mCompany, id, ChronoUnit.FOREVER.getDuration()).get();
		
		Assert.assertEquals(car, carFromManager);
	}
	
	@Test
	public void isNewEnougthTest(){
		Car car = new Car(1L, -19.35, -41.0, LocalDateTime.now());
		
		MapPointManager<Car> manager = new MapPointManager<>();
					
		Method method;
		try {
			method = manager.getClass().getDeclaredMethod("isNewEnougth", LocalDateTime.class, Duration.class);
			method.setAccessible(true);
			try {
				Assert.assertTrue((boolean) method.invoke(manager, car.getLastUpdate(), ChronoUnit.FOREVER.getDuration()));
				Assert.assertFalse((boolean) method.invoke(manager, car.getLastUpdate(), Duration.ZERO));
				
				return;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert(false);
	}
	
	@Test
	public void companyCreateTest(){
		String company = "Simple Pass";
		
		Car car = new Car(1L, -19.35, -41.0, LocalDateTime.now());
		
		MapPointManager<Car> manager = new MapPointManager<>();
		
		manager.insertPoint(company, car);
		
		Optional<Map<Long, Car>> optionalMap = manager.pointsOfCompany(
				company, 
				ChronoUnit.FOREVER.getDuration());
		
		Assert.assertTrue(optionalMap.isPresent());
		Assert.assertTrue(optionalMap.get().get(1L).equals(car));
	}
	
	@Test
	public void insertMapPointTest(){
		String company = "Simple Pass";
		
		Car car = new Car(1L, -19.35, -41.0, LocalDateTime.now());
		MapPointManager<Car> mManager = new MapPointManager<>();	
		
		mManager.insertPoint(company, car);
		
		Optional<Map<Long, Car>> optionalMap = mManager.pointsOfCompany(
				company, 
				ChronoUnit.FOREVER.getDuration());
		
		Assert.assertTrue(optionalMap.isPresent());
		Assert.assertTrue(optionalMap.get().get(1L).equals(car));		
	}
	
	@Test
	public void clearOldPoints(){
		MapPointManager<Car> manager = new MapPointManager<>();
		
		String company = "Simple Pass";
		
		//Insert point. Last update in 2 hours
		Car car = new Car(1L, -19.35, -41.0, LocalDateTime.now().minusHours(2));
		
		manager.insertPoint(company, car);
		
		//Query points. Younger than forever
		Optional<Map<Long, Car>> optionalMap = 
				manager.pointsOfCompany(company, ChronoUnit.FOREVER.getDuration());
		
		Assert.assertTrue(optionalMap.isPresent());
		Assert.assertTrue(optionalMap.get().get(1L).equals(car));
		
		//Remove point older than 5 minutes
		manager.clearOldPoints(ChronoUnit.MINUTES.getDuration().multipliedBy(5));
		
		//Query points. Younger than 5 minutes
		Optional<Map<Long, Car>> optionalMap2 = 
				manager.pointsOfCompany(company, ChronoUnit.MINUTES.getDuration().multipliedBy(5));
		
		Assert.assertTrue(optionalMap2.isPresent());
		Assert.assertTrue(optionalMap2.get().isEmpty());
		
	}
	

}
