# Dealership Inventory Management System

![Dealership](resources/dealership.jpg)

## Introduction

The **Dealership Inventory Management System** is a Java-based software system designed to manage and streamline the inventory operations of a car dealership. This system offers a range of features to efficiently handle vehicle data, dealer interactions, and inventory management.

Repository Link: [Car Dealership Java](https://github.com/Guacamoley/Car-Dealership-Java)

## Getting Started

To get started with the **Dealership Inventory Management System**, follow the instructions below:

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/yourusername/Car-Dealershio-Java.git

## How to Run

To run the **Dealership Inventory Management System**, follow these steps:

1. Head into the `src` folder of the repository.

2. Navigate to the `Main` folder within the `src` folder.

3. Locate the `Main.java` file.

4. Run the `Main.java` file using your preferred Java development environment or IDE.

   - If you are using an IDE like IntelliJ IDEA or Eclipse, you can right-click on the `Main.java` file and select "Run" to execute the program.

   - If you prefer using the command line, navigate to the `Main` folder and compile and run the program with the following commands:
   
     ```bash
     javac Main.java
     java Main
     ```

5. The program will start, and you can use the graphical user interface (GUI) to perform various operations and manage the dealership's inventory efficiently.

Enjoy using the **Dealership Inventory Management System** for your car dealership operations!

## Features

### 1. Read JSON Vehicle Data

The software is capable of reading vehicle information from JSON files, making it easy to import and manage vehicle data efficiently.

### 2. Support for Multiple Vehicle Types

It supports four different types of vehicles in the input file: SUV, Sedan, Pickup, and Sports Car, allowing for diverse inventory management.

### 3. Store Vehicle Information

The system reads and stores essential vehicle details, including Vehicle ID, Manufacturer, Model, Acquisition Date, and Price, associating them with the respective dealer IDs.

### 4. Metadata Handling

It efficiently manages associated metadata for each vehicle, ensuring comprehensive vehicle data management.

### 5. Dealer Commands

The system provides dealers with commands to add incoming vehicles, enable dealer vehicle acquisition, and disable dealer vehicle acquisition, facilitating efficient dealer operations.

### 6. Vehicle Addition Control

Incoming vehicles can only be added to a dealer if they have enabled vehicle reception, ensuring strict control over inventory.

### 7. Dealer Record Keeping

Even when vehicle reception is disabled, the system continues to maintain records for the dealer while preventing new incoming vehicles.

### 8. Export Vehicle Data

It offers the capability to export all vehicles associated with a dealer into a single JSON file, simplifying data exchange and backup.

### 9. Display Current Vehicles

The system displays the list of current vehicles for each dealer, providing an overview of their inventory.

### 10. Vehicle Loan Tracking

The software allows dealers to track cars that are on loan and not available for sale, enhancing visibility into vehicle availability.

### 11. Persistent Data Storage

The system retains vehicle and dealer data between program restarts, eliminating data loss and improving usability.

### 12. Import from XML

It supports importing vehicle data from XML files, accommodating dealers with different data formats. The dealer name can also be edited/added for previous records.

### 13. Inventory Transfers

Dealers can transfer inventory between each other, streamlining inventory management between multiple dealerships.

### 14. Graphical User Interface (GUI)

The system includes a user-friendly graphical interface, allowing users to perform operations through a point-and-click interface for enhanced usability.

## Acknowledgments

This project acknowledges the contributions and requirements of car dealerships in managing their inventory efficiently. It aims to provide a robust and user-friendly solution for inventory management.

For additional details and usage guidelines, refer to the repository's documentation.

*Note: The loaning of cars, as mentioned in Feature 10, is handled by separate software and is not part of this project's scope.*

![Dealership](resources/dealership.jpg)

*Image Source: [[Unsplash](https://unsplash.com/photos/P0IvTlcl_s0)](https://unsplash.com/photos/3WAMh1omVAY)*

Enjoy using the **Dealership Inventory Management System** for your car dealership operations!
