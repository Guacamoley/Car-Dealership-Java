package GUI;

import javax.swing.*;

import DealershipSystem.Car;

public class AddVehicle {
    AddVehicleInput adder = new AddVehicleInput();
    private String dealerID, vehType, vehManu, vehModel, vehID;
    private Double price;
    private long acqDate;
    private final Car car = new Car(dealerID, vehType, vehManu, vehModel, vehID, price, acqDate);

    public Car getVehicleInput(Car car) {
        adder.receiveVehType();
        adder.receiveVehManu();
        adder.receiveVehModel();
        adder.receiveVehID();
        adder.receiveVehPrice();
        adder.receieveVehDate();
        return car;
    }
}
