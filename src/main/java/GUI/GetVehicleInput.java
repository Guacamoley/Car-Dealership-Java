package GUI;

import javax.swing.*;

/**
 * This class takes user input for various Car fields and then returns the input for use later in creating a new car.
 *
 * @author Michael Ha
 */

public class GetVehicleInput {
    /**
     * Gets the dealership ID from user input.
     *
     * @return The dealer ID.
     */
    protected String receiveDealerID() {
        return createDialogueBox("Dealership ID: ");
    }

    /**
     * Gets the vehicle type from user input. The loop will check if the entered type is one of the four types listed
     * (suv, sedan, pickup, sports car).
     *
     * @return The vehicle type.
     */
    protected String receiveVehType() {
        String userInput = createDialogueBox("Vehicle Type: suv, sedan, pickup, sports car");
        while (!userInput.equalsIgnoreCase("suv") && !userInput.equalsIgnoreCase("sedan") && !userInput.equalsIgnoreCase("pickup") && !userInput.equalsIgnoreCase("sports car")) {
            userInput = createDialogueBox("Vehicle Type: suv, sedan, pickup, sports car");
        }
        return userInput;
    }

    /**
     * Gets the vehicle manufacturer from user input. The loop will check if input is using valid characters.
     *
     * @return The vehicle manufacturer
     */
    protected String receiveVehManu() {
        String userInput = createDialogueBox("Vehicle manufacturer");
        while (!userInput.matches("[a-zA-Z]+"))
            userInput = createDialogueBox("valid Vehicle manufacturer");
        return userInput;
    }

    /**
     * Gets the vehicle model from user input. The loop will check if the entered characters are all letters.
     *
     * @return The vehicle model.
     */
    protected String receiveVehModel() {
        String userInput = createDialogueBox("Vehicle Model");
        while (!userInput.matches("[a-zA-Z]+")) userInput = createDialogueBox("valid Vehicle Model");
        return userInput;
    }

    /**
     * Gets the vehicle ID from user input. The loop will run if user enters any special characters.
     *
     * @return The vehicle ID.
     */
    protected String receiveVehID() {
        String userInput = createDialogueBox("Vehicle ID");
        while (!userInput.matches("[0-9a-zA-Z]+")) userInput = createDialogueBox("valid Vehicle ID");
        return userInput;
    }

    /**
     * Gets the vehicle price from user input. The loop will check if the entered characters are all numbers.
     *
     * @return The vehicle price
     */
    protected Double receiveVehPrice() {
        String userInput = createDialogueBox("Vehicle Price");
        while (!userInput.matches("[0-9]+")) userInput = createDialogueBox("valid Vehicle Price");
        return Double.valueOf(userInput);
    }

    /**
     * Gets the vehicle acquisition date from user input. The loop will check if the entered characters are all numbers.
     *
     * @return The acquisition date
     */
    protected long receieveVehDate() {
        String userInput = createDialogueBox("Acquisition Date");
        while (!userInput.matches("[0-9]+")) userInput = createDialogueBox("valid Acquisition Date");
        return Long.parseLong(userInput);
    }

    /**
     * Method used to create a dialogue box for the user to input information into.
     *
     * @param entry The string value that whill be concatenated at the end of the inputDialog string
     * @return showInputDialog box for user input
     */
    protected String createDialogueBox(String entry) {
        return JOptionPane.showInputDialog("Please type in the " + entry);
    }
}




