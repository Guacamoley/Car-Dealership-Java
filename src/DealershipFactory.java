import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the list of all dealerships. It takes incoming car objects
 * and adds them to the corresponding dealership and creates the dealership if
 * needed.
 * 
 * @author Paul Schmitz
 *
 */
public class DealershipFactory {

	// fields

	/**
	 * holds all of the car dealerships that currently exist
	 */
	private List<Dealership> dealerships;

	// constructors

	/**
	 * standard constructor which starts an empty array list for dealerships.
	 */
	public DealershipFactory() {
		dealerships = new ArrayList<Dealership>();
	}

	// methods

	/**
	 * getter for the dealerships list
	 * @return dealerships
	 */
	public List<Dealership> getDealerships() {
		return dealerships;
	}

	/**
	 * adds one car to a dealership using the car's dealership id. will create a new
	 * dealership if needed.
	 * 
	 * @param car the car to add
	 * @return null if the car is successfully added, otherwise return the invalid
	 *         car.
	 */
	public Car addCar(Car car) {
		// TODO
		// get car's dealership ID
		// if car has no dealership ID, return the car object
		// search existing dealerships for ID
		// if none exists, create dealership with that ID
		// add car to dealership
		// if unable to add car, return the car object
		return null;
	}

	/**
	 * adds a list of cars to their respective dealerships using each car's
	 * dealership id. will create new dealerships if needed.
	 * 
	 * @param cars a list of cars to add
	 * @return a list of all invalids cars which could not be added.
	 */
	public List<Car> addCar(List<Car> cars) {
		// the list of invalid cars to be returned
		List<Car> invalidCars = new ArrayList<Car>();
		
		// TODO
		// for each car in list, addCar(car)
		// if non-null object is returned, add to invalidCars list

		return invalidCars;
	}
}
