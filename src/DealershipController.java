import java.util.ArrayList;
import java.util.List;

/**
 * This class manages a list of dealerships. It takes incoming car objects and
 * adds them to the corresponding dealership by matching dealership Id. It
 * creates new dealerships if needed. It will not add cars to a dealership if
 * acquiring is not enabled. Newly created dealerships have acquiring enabled by
 * default.
 * 
 * @author Paul Schmitz
 *
 */
public class DealershipController {

	// FIELDS

	/**
	 * holds all of the car dealerships that currently exist
	 */
	private List<Dealership> dealerships;

	// CONSTRUCTORS

	/**
	 * standard constructor which starts an empty array list for dealerships.
	 */
	public DealershipController() {
		dealerships = new ArrayList<Dealership>();
	}

	// METHODS

	/**
	 * getter for the dealerships list
	 * 
	 * @return dealerships
	 */
	public List<Dealership> getDealerships() {
		return dealerships;
	}

	/**
	 * adds one car to a dealership using the car's dealership id. will create a new
	 * dealership if needed. newly created dealerships are set to be able to acquire
	 * cars by default.
	 * 
	 * @param car the car to add
	 * @return null if the car is successfully added, otherwise return the invalid
	 *         car. if the car being added is null, it will not be added, but null
	 *         is still returned.
	 */
	public Car addCar(Car car) {
		// get car's dealership ID
		String currentDealershipId = null;
		if (car == null) {
			return null;
		} else {
			currentDealershipId = car.getDealership_id();
		}

		// if car has no dealership ID, return the car object
		if (currentDealershipId == null) {
			return car;
		}

		// search existing dealerships for ID
		Dealership currentDealership = findDealershipById(currentDealershipId);

		// if none exists, create dealership with that ID
		if (currentDealership == null) {
			currentDealership = new Dealership(currentDealershipId);
		}

		// check if dealership is able to acquire cars
		if (!currentDealership.isAcquireEnabled()) {
			return car;
		}

		// add car to dealership
		currentDealership.addCar(car);

		// if unable to add car, return the car object, otherwise return null
		return null;
	}

	/**
	 * helper function searches current dealerships for one matching the provided
	 * Id.
	 * 
	 * @param dealershipId to find
	 * @return the first dealership with matching id, or null if none exists.
	 */
	private Dealership findDealershipById(String dealershipId) {
		for (int i = 0; i < dealerships.size(); i++) {
			if (dealerships.get(i).getDealershipId().equals(dealershipId)) {
				return dealerships.get(i);
			}
		}
		return null;
	}

	/**
	 * adds a list of cars to their respective dealerships using each car's
	 * dealership id. will create new dealerships if needed.
	 * 
	 * @param cars a list of cars to add
	 * @return a list of all invalids cars which could not be added. if none
	 *         invalid, then the list is empty. if the input list contains null
	 *         entries, then those will not be added, but they will also not appear
	 *         on the invalid list.
	 */
	public List<Car> addCars(List<Car> cars) {
		// the list of invalid cars to be returned
		List<Car> invalidCars = new ArrayList<Car>();

		// for each car in list, addCar(car)
		for (int i = 0; i < cars.size(); i++) {

			// addCar method will return any invalidCars, so they must be collected here
			Car invalidCar = addCar(cars.get(i));

			// if non-null object is returned, add to invalidCars list
			if (invalidCar != null) {
				invalidCars.add(invalidCar);
			}
		}

		return invalidCars;
	}
	
	public void setDealershipAcquireEnabled(String dealershipId, boolean acquireEnabled) {
		Dealership dealership = findDealershipById(dealershipId);
		dealership.setAcquireEnabled(acquireEnabled);
	}
	
	public List<Car> getDealershipCars(String dealershipId) {
		Dealership dealership = findDealershipById(dealershipId);
		return dealership.getCars();
	}
}
