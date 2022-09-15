import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an inventory of cars distributed across multiple
 * dealerships. The public methods represent the fundamental functional
 * requirements. New car additions are handled using a DealershipController,
 * which controls a particular list of dealerships ensuring that cars are added
 * to the correct dealership.
 * 
 * @author Paul Schmitz
 *
 */
public class Inventory {

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

	public void importFile() {
		// TODO
		// somehow retrieve the list of cars to be added
		List<Car> importedList = new ArrayList<Car>();

		// add the list of cars and record a list of all cars that failed to add
		List<Car> failList = dc.addCars(importedList);
		// TODO: failList can be used here to display error messages or something on the
		// interface
	}

	/**
	 * returns the list of all dealership id strings. TODO: could this be used for a
	 * drop-down box or something where you select which dealership you want to
	 * interact with?
	 * 
	 * @return list of all dealership id's
	 */
	public List<String> getAllDealershipIds() {
		List<String> dealershipIds = new ArrayList<String>();
		List<Dealership> dealerships = dc.getDealerships();
		for (int i = 0; i < dealerships.size(); i++) {
			dealershipIds.add(dealerships.get(i).getDealershipId());
		}
		return dealershipIds;
	}

	/**
	 * adds one new vehicle per assignment requirement #5. the car object in the
	 * parameter must have its dealership Id field setup ahead of time. the system
	 * automatically places the car at its dealership, and it checks whether or not
	 * the dealership can acquire. TODO: depending on the desired usage, this method
	 * could also be changed to accept each of the car parameters separately and
	 * then create the car internally, such as addIncomingVehicle(String dealerId,
	 * String carType, ...)
	 * 
	 * @param car to be added
	 */
	public void addIncomingVehicle(Car car) {
		dc.addCar(car);
	}

	/**
	 * toggles this dealership so that it can acquire future cars.
	 * 
	 * @param dealershipId
	 */
	public void enableDealerAcquisition(String dealershipId) {
		dc.setDealershipAcquireEnabled(dealershipId, true);
	}

	/**
	 * toggles this dealership so that it cannot acquire future cars.
	 * 
	 * @param dealershipId
	 */
	public void disableDealerAcquisition(String dealershipId) {
		dc.setDealershipAcquireEnabled(dealershipId, false);
	}

	public void exportFile(String dealershipId) {
		// TODO, sample code:
		// List<Car> myList = getDealershipCars(String dealershipId);
	}

	/**
	 * gets list of all cars at the given dealership. TODO: depending on usage, this
	 * function could convert the cars into strings and return a string list, if
	 * that would be easier for the interface to handle.
	 * 
	 * @param dealershipId
	 * @return list of all cars at this dealership
	 */
	public List<Car> getCars(String dealershipId) {
		return dc.getDealershipCars(dealershipId);
	}
}
