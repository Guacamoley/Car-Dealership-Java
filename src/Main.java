/*
 * Evan, Michael, Rodnee, Gabe, Paul
 * Group 1 Project 1
 * ICS 372-01
 * Professor Tim Carlson
 */

//import GUI.Interface;

import DealershipSystem.Inventory;
import DealershipSystem.Json;
import GUI.Interface;

// Main runner class for project
public class Main {

	// Create the car/dealer inventory, which can be utilized by the interface

	public static void main(String[] args) {
		// sample inventory method usage

		// Creates a new user interface for interaction
		new Interface().createInterface();

		// -Put the interface above into comments and undo this comment to print
		// everything from JSON file
		//Json c = new Json();
		//c.readFile();

	}
}