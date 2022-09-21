package GUI;
import javax.swing.*;

import DealershipSystem.Car;
import DealershipSystem.DealershipController;

import java.util.InputMismatchException;

public class RemoveVehicleInput {
    private String vehId;
    private String dealershipId;

    private final Car car = new Car(dealershipId, vehId);
    private DealershipController dc;

    public Car addNewVehicle() {
        receiveDealerID();
        receiveVehID();
        return car;
    }

    private void receiveDealerID() {
        do {
            try {
                String userInput = createDialogueBox("5 digit Dealership ID");

                if (userInput.length() == 5 && userInput.matches("[0-9]+"))
                    car.setDealership_id(userInput);
            } catch (InputMismatchException e) {
            }
        } while (car.getDealership_id() == null);
    }

    private void receiveVehID() {
        do {
            try {
                String userInput = createDialogueBox("Vehicle ID");
                car.setVehicle_id(userInput);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a vehicle id");
            }
        } while (car.getVehicle_id() == null);
    }

    private String createDialogueBox(String entry) {
        return JOptionPane.showInputDialog("Please type in the " + entry);
    }
}
