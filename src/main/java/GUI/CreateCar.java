package GUI;

import DealershipSystem.Car;

/**
 * Class to create a new car from the user input from GetVehicleInput.
 * */

public class CreateCar {
    GetVehicleInput getInput = new GetVehicleInput();
    private String dealerID, vehType, vehManu, vehModel, vehID;
    private Double price;
    private long acqDate;
    private final Car car = new Car(null, vehType, vehManu, vehModel, vehID, price, acqDate);

    public Car addNewVehicle() {
        car.setVehicle_type(getInput.receiveVehType());
        car.setVehicle_manufacturer(getInput.receiveVehManu());
        car.setVehicle_model(getInput.receiveVehModel());
        car.setVehicle_id(getInput.receiveVehID());
        car.setPrice(getInput.receiveVehPrice());
        car.setAcquisition_date(getInput.receieveVehDate());
        return car;
    }
}
