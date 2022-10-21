/*
 * Evan, Michael, Rodnee, Gabe, Paul
 * Group 1 Project 1
 * ICS 372-01
 * Professor Tim Carlson
 */

//import GUI.Interface;
import DealershipSystem.xmlDealers;
import GUI.Interface;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

// Main runner class for project
public class Main {

    public static void main(String[] args) {
        // Creates the GUI for the dealership system. All operations will be done in the GUI.
        //new Interface().createInterface();

        //Delete later this is for testing
        xmlDealers xmlDeal = new xmlDealers();

        xmlDeal.xmlUnmarshal();
    }
}


