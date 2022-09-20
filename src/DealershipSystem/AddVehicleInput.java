package DealershipSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * This class will grab user input when they are adding a vehicle. The dialogue will ask the user questions and save
 * those to the specified dealership. If there is no dealership, then it'll create a dealership.
 */

/**
 * TODO: create a method in this class that adds the car to the dealership (should be easy since we already have
 *  method for that.
 *  Pseudocode:
 *  -check if dealership exists
 *  if not, then create dealership
 *  finally add car to that dealership
 * */

public class AddVehicleInput {

//    Creates a new car object to be added
    private String dealerID, vehType, vehManu, vehModel, vehID;
    private Double price;
    private long acqDate;
    private Car car = new Car(dealerID, vehType, vehManu, vehModel, vehID, price, acqDate);

//    Single method that the Add Button will call when the user wants to add a car
    public Car addNewVehicle() {
        receiveDealerID();
        receiveVehType();
        receiveVehManu();
        receiveVehModel();
        receiveVehID();
        receiveVehPrice();
        receieveVehDate();
        return car;
    }

    /**
     * Gets the dealership ID from user input. The loop will make sure the length of the input is 5 characters
     * and all characters are type int. If the input passes, then it'll change the dealership id. If not, then it should
     * return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receiveDealerID() {
        do {
            try {
                String userInput = createDialogueBox("5 digit Dealership ID");

                if (userInput.length() == 5 && userInput.matches("[0-9]+"))
                    car.setDealership_id(userInput);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a 5 digit dealership id");
            }
        } while (car.getDealership_id() == null);
    }

    /**
     * Gets the vehicle type from user input. The loop will check if the entered type is one of the four types listed
     * (suv, sedan, pickup, sports car). If the input passes, then it'll change the vehicle type. If not, then it
     * should return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receiveVehType() {
        do {
            try {
                String userInput = createDialogueBox("Vehicle Type: suv, sedan, pickup, sports car");

                if (userInput.equalsIgnoreCase("suv") || userInput.equalsIgnoreCase("sedan") ||
                        userInput.equalsIgnoreCase("pickup") || userInput.equalsIgnoreCase("sports car"))
                    car.setVehicle_type(userInput);
            } catch (InputMismatchException e) {
                System.out.println("Please enter the correct vehicle type");
            }
        } while (car.getVehicle_type() == null);
    }

    /**
     * Gets the vehicle type from user input. The loop will check if the entered type is one of the four types listed
     * (suv, sedan, pickup, sports car). If the input passes, then it'll change the vehicle type. If not, then it
     * should return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receiveVehManu() {
        do {
            try {
                String userInput = createDialogueBox("Vehicle manufacturer");
                if (userInput.matches("[a-zA-Z]+"))
                    car.setVehicle_manufacturer(userInput);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a manufacturer");
            }
        } while (car.getVehicle_manufacturer() == null);
    }

    /**
     * Gets the vehicle model from user input. The loop will check if the entered characters are all letters.
     * If the input passes, then it'll change the vehicle model. If not, then it
     * should return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receiveVehModel() {
        do {
            try {
                String userInput = createDialogueBox("Vehicle Model");
                if (userInput.matches("[a-zA-Z]+"))
                    car.setVehicle_model(userInput);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a model");
            }
        } while (car.getVehicle_model() == null);
    }

    /**
     * Gets the vehicle ID from user input. The loop will check if the entered characters are all numbers.
     * If the input passes, then it'll change the vehicle ID. If not, then it
     * should return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receiveVehID() {
        do {
            try {
                String userInput = createDialogueBox("Vehicle ID");
                if (userInput.matches("[0-9]+"))
                    car.setVehicle_id(userInput);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a vehicle id");
            }
        } while (car.getVehicle_id() == null);
    }

    /**
     * Gets the vehicle price from user input. The loop will check if the entered characters are all letters.
     * If the input passes, then it'll change the vehicle price. If not, then it
     * should return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receiveVehPrice() {
        do {
            try {
                String userInput = createDialogueBox("Vehicle Price");
                if (userInput.matches("[0-9]+"))
                    car.setPrice(Double.valueOf(userInput));
            } catch (InputMismatchException e) {
                System.out.println("Please enter a vehicle vehicle price");
            }
        } while (car.getPrice() == null);
    }

    /**
     * Gets the vehicle acquisition date from user input. The loop will check if the entered characters are all numbers.
     * If the input passes, then it'll change the vehicle acquisition date.
     * If not, then it should return an error box and loop again for user input.
     *
     * TODO: Get user feedback box to appear if a bad entry was made
     * */
    private void receieveVehDate() {
        do {
            try {
                String userInput = createDialogueBox("Acquisition Date");
                if (userInput.matches("[0-9]+"))
                    car.setAcquisition_date(Long.parseLong(userInput));
            } catch (InputMismatchException e) {
                System.out.println("Please enter the acquisition date for the vehicle");
            }
        } while (car.getAcquisition_date() < 1);
    }

//    Method used to create an input dialogue box.
    private String createDialogueBox(String entry) {
        return JOptionPane.showInputDialog("Please type in the " + entry);
    }


}




