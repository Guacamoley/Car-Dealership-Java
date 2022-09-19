package DealershipSystem;

import java.io.File;
import java.util.List;

/**
 * This class represents an inventory of cars distributed across multiple
 * dealerships. The public methods represent the fundamental functional
 * requirements. New car additions are handled using a
 * DealershipSystem.DealershipController, which controls a particular list of
 * dealerships ensuring that cars are added to the correct dealership.
 * 
 * @author Paul Schmitz
 *
 */
public class Inventory {
	Json c = new Json();

	// FIELDS

	/*
	 * the dealership controller responsible for handling the creation of new
	 * dealerships and the assignment of new cars to those dealerships.
	 */
	private DealershipController dc;

	// CONSTRUCTORS

	public Inventory() {
		dc = new DealershipController();
	}

	// METHODS

	/**
	 * Passes the file to the json reader to get the list of car objects. Then, the
	 * objects are added to the inventory thru the dealership controller. This
	 * places each car into its correct dealership. The list of cars that failed to
	 * be added is returned.
	 * 
	 * @param file the json file to import
	 * @return the list of cars that failed to be added, probably because they have
	 *         no dealership id.
	 */
	public List<Car> importFile(File file) {
		return dc.addCars(c.readFile(file));
	}

	/**
	 * returns the list of all dealership id strings.
	 * 
	 * @return list of all dealership id's
	 */
	public List<String> getAllDealershipIds() {
		return dc.getDealershipIds();
	}

	/**
	 * adds one new vehicle per assignment requirement #5. returns true if and only
	 * if the car was successfully added. this requires the car's dealership Id to
	 * be non-null. a null car parameter will return false.
	 * 
	 * @param car the car to add
	 * @return true if the car was successfully added to the inventory system.
	 *         otherwise false.
	 */
	public boolean addIncomingVehicle(Car car) {
		if (car == null)
			return false;
		else {
			return dc.addCar(car) == null;
		}
	}

	/**
	 * checks if the dealership exists and allows acquisitions.
	 * 
	 * @param dealershipId the dealership id to check
	 * @return true iff the dealership exists in the system and it allows
	 *         acquisitions.
	 */
	public boolean getDealerAcquisition(String dealershipId) {
		return dc.getDealershipAcquireEnabled(dealershipId);
	}

	/**
	 * toggles this dealership so that it can acquire future cars.
	 * 
	 * @param dealershipId the dealership id to enable
	 */
	public void enableDealerAcquisition(String dealershipId) {
		dc.setDealershipAcquireEnabled(dealershipId, true);
	}

	/**
	 * toggles this dealership so that it cannot acquire future cars.
	 * 
	 * @param dealershipId the dealership id to disable
	 */
	public void disableDealerAcquisition(String dealershipId) {
		dc.setDealershipAcquireEnabled(dealershipId, false);
	}

	/**
	 * TODO: export all cars for this dealership to a json file.
	 * 
	 * @param dealershipId
	 */
	public void exportFile(String dealershipId) {
		// sample code:
		// List<DealershipSystem.Car> myList = getDealershipCars(String dealershipId);
	}

	/**
	 * gets one string which includes all cars at the specified dealership. the
	 * specified delimiter string is inserted between each entry. returns "" if
	 * there are no entries.
	 * 
	 * @param dealershipId the dealership id to retrieve from
	 * @param delimiter    the string to insert between each entry
	 * @return string including all cars from the dealership with delimiters
	 *         inserted
	 */
	public String getCars(String dealershipId, String delimiter) {
		String result = "";
		List<Car> cars = dc.getDealershipCars(dealershipId);
		for (Car c : cars) {
			result += c.toString() + delimiter;
		}
		return result;
	}
}
