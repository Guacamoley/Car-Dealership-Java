package DealershipSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages a list of dealerships and the cars at those dealerships.
 * Note that dealerships contain a list of cars. This class takes incoming car
 * objects and adds them to the corresponding dealership by matching the car's
 * dealership id to the existing dealership's id. If an incoming car's
 * dealership id does not match a dealership, a new dealership is created and
 * added to the managed list. This class cannot add cars to a dealership if
 * acquiring is not enabled for that dealership. Newly created dealerships have
 * acquiring enabled by default.
 * 
 * @author Paul Schmitz
 *
 */
public class DealershipController {

	// FIELDS

	/**
	 * holds all of the car dealerships that currently exist. dealerships added to
	 * this list should have unique dealership id's since that parameter is used as
	 * a search key.
	 */
	private Map<String, Dealership> dealerships;

	// CONSTRUCTORS

	/**
	 * standard constructor which starts an empty array list for dealerships.
	 */
	public DealershipController() {
		dealerships = new HashMap<String, Dealership>();
	}

	// METHODS
	/**
	 * adds one car to a dealership by matching the car's dealership id to the
	 * dealership's id. will create a new dealership if needed. doesn't add anything
	 * if the dealership is setup to not allow acquisitions. newly created
	 * dealerships are set to allow acquisitions by default. returns the car object
	 * if unable to add it, otherwise returns null. if the input parameter is null,
	 * it does nothing and returns null. the idea is that it will return any
	 * instantiated objects that failed to add.
	 * 
	 * @param car the car to add
	 * @return null if the car is successfully added, otherwise return the invalid
	 *         car. if the car being added is null, it will not be added, but null
	 *         is still returned.
	 */
	public Car addCar(Car car) {
		// get car's dealership ID
		String dealershipId = null;
		if (car == null) {
			return null;
		} else {
			dealershipId = car.getDealership_id();
		}

		// if car has no dealership ID, return the car object
		if (dealershipId == null) {
			return car;
		}

		// search existing dealerships for ID
		Dealership dealership = dealerships.get(dealershipId);

		// if none exists, create dealership with that ID
		if (dealership == null) {
			dealership = new Dealership(dealershipId);
			dealerships.put(dealershipId, dealership);
		}

		// check if dealership is able to acquire cars
		if (!dealership.isAcquireEnabled()) {
			return car;
		}

		// add car to dealership
		dealership.addCar(car);

		// returns null because the car was successfully added
		return null;
	}

	/**
	 * adds a list of cars to their respective dealerships using each car's
	 * dealership id. will create new dealerships if needed. returns a list of cars
	 * that were not successfully added, likely because they are have no dealership
	 * id. null entries on the input list are essentially ignored and will not show
	 * up on the returned list.
	 * 
	 * @param cars a list of cars to add
	 * @return a list of all invalids cars which could not be added. if none
	 *         invalid, then the list is empty. if the input list contains null
	 *         entries, then those will not be added, and they will also not appear
	 *         on the invalid list.
	 */
	public List<Car> addCars(List<Car> cars) {
		// the list of invalid cars to be returned
		List<Car> invalidCars = new ArrayList<Car>();

		// check for null argument
		if (cars != null) {

			// for each car in list, addCar(car)
			for (int i = 0; i < cars.size(); i++) {

				// add each car, and collect any invalid cars that are returned
				Car invalidCar = addCar(cars.get(i));

				// if non-null object is returned, add to invalid cars list
				if (invalidCar != null) {
					invalidCars.add(invalidCar);
				}
			}
		}

		return invalidCars;
	}

	/**
	 * sets whether or not a given dealership can acquire new cars.
	 * 
	 * @param dealershipId   the dealership id to affect
	 * @param acquireEnabled the boolean value to set
	 */
	public void setDealershipAcquireEnabled(String dealershipId, boolean acquireEnabled) {
		Dealership dealership = dealerships.get(dealershipId);
		if (dealership != null) {
			dealership.setAcquireEnabled(acquireEnabled);
		} else {
			return;
		}
	}

	/**
	 * gets the list of all cars belonging to the given dealership id. returns an
	 * empty list if the dealership cannot be found.
	 * 
	 * @param dealershipId the dealership id to match
	 * @return the list of all cars for this dealership
	 */
	public List<Car> getDealershipCars(String dealershipId) {
		Dealership dealership = dealerships.get(dealershipId);
		if (dealership != null) {
			return dealership.getCars();
		} else {
			return new ArrayList<Car>();
		}
	}

	/**
	 * gets the list of all current dealership id's.
	 * 
	 * @return the list of all dealership id's
	 */
	public List<String> getDealershipIds() {
		return new ArrayList<String>(dealerships.keySet());
	}
}
