package DealershipSystem;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    /*
    * TODO:
    * Attributes:
    *   -dealershipID
    *   (+)canAcquire - public or private?
    *   -inventory
    *
    * Methods:
    *   +addVehicle()
    *   +enableAcquisition()
    *   +disableAcquisition()
    *   +listVehicles()
    *   +toString()
    *   +getters() & setters()
    * */
	
	/*
	 *  Note: it was necessary to add the following skeleton code in order 
	 *  to start building the DealershipSystem.DealershipController class.
	 */
	
	// FIELDS
	
	private List<Car> cars;
	private String dealershipId;
	private boolean acquireEnabled;
	
	// CONSTRUCTORS
	
	public Dealership(String dealershipId) {
		cars = new ArrayList<Car>();
		this.dealershipId = dealershipId;
		acquireEnabled = true;
	}
	
	// METHODS
	
	public void addCar(Car car) {
		cars.add(car);
	}
	
	public List<Car> getCars(){
		return cars;
	}
	
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	/**
	 * @return the dealershipId
	 */
	public String getDealershipId() {
		return dealershipId;
	}

	/**
	 * @param dealershipId the dealershipId to set
	 */
	public void setDealershipId(String dealershipId) {
		this.dealershipId = dealershipId;
	}

	/**
	 * @return the canAcquire
	 */
	public boolean isAcquireEnabled() {
		return acquireEnabled;
	}

	/**
	 * @param canAcquire the canAcquire to set
	 */
	public void setAcquireEnabled(boolean acquireEnabled) {
		this.acquireEnabled = acquireEnabled;
	}
	
}
