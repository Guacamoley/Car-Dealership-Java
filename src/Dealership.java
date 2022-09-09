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
	
	// fields
	private List<Car> cars;
	
	// constructors
	public Dealership() {
		cars = new ArrayList<Car>();
	}
	
	// methods
	public List<Car> getCars(){
		return cars;
	}
	
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
}
