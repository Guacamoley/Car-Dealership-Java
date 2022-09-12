/*
-This is a prototype of the JSON reader with a GSON library intended for testing
-upload this on github and figure out how to upload gson library


 */





import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String [] args){
        File input = new File("C:/Users/User 2/Desktop/Project1_input.json");

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            //Process all cars
            JsonArray jsonArrayOfCars = fileObject.get("car_inventory").getAsJsonArray();
            List<Car> cars = new ArrayList<>();

            for(JsonElement carElement : jsonArrayOfCars){
                //Get the Json Object:
                JsonObject carJsonObject = carElement.getAsJsonObject();



                //Extract data
               String dealershipID = null;
                String vehicleType = null;
                String vehicleManu = null;
                String vehicleModel = null;
                String vehicleID = null;
                Double price = null;
                long acquisitionDate = carJsonObject.get("acquisition_date").getAsLong();

                //Construct Long
                Long acquisitionDateWrap = acquisitionDate;

                /*
                -Input validation if the object attributes are empty inside the object to prevent an exception from occurring
                -I would like to make this more efficient by putting this through a loop for every object, and it's associated attributes if it's null or not
                 */

                if(carJsonObject.has("dealership_id")) {
                    dealershipID = carJsonObject.get("dealership_id").getAsString();
                }
                if(carJsonObject.has("vehicle_type")) {
                    vehicleType = carJsonObject.get("vehicle_type").getAsString();
                }
                if(carJsonObject.has("vehicle_model")) {
                    vehicleModel = carJsonObject.get("vehicle_model").getAsString();
                }
                if(carJsonObject.has("vehicle_manufacturer")) {
                    vehicleManu = carJsonObject.get("vehicle_manufacturer").getAsString();
                }
                if(carJsonObject.has("vehicle_id")) {
                    vehicleID = carJsonObject.get("vehicle_id").getAsString();
                }


                /*
                -I'm not sure if you can leave primitives empty in the JSON file because it's part of the syntax? Therefore it will just go straight to the exception instead?
                -Needs further investigation
                -Exporting you can put in null but importing you can't unless if you directly tinker the file and place the data type as null
                 */
                if(carJsonObject.has("price")) {
                    price = carJsonObject.get("price").getAsDouble();
                }
                if(carJsonObject.has("acquisition_date")) {
                    acquisitionDate = carJsonObject.get("acquisition_date").getAsLong();
                }




                Car car = new Car(dealershipID, vehicleType, vehicleManu, vehicleModel, vehicleID, price, acquisitionDate);
                //array list for storing cars and their attributes
                cars.add(car);

            }

            //console print
            System.out.println("Entire car inventory: " + cars);
            //calling a specific car and it's associated data
            System.out.println("Car 2: " + cars.get(2));
            //Querying specific stuff example
            String carTwoDealershipID = cars.get(2).dealership_id;
            System.out.println("Car 2 Dealership ID: " + carTwoDealershipID);


        //Error for input file
        } catch (FileNotFoundException e) {
            System.err.println("Error input file not found");
            e.printStackTrace();
        }
        //Error if attribute data types are incorrect
        catch(Exception e){
            System.err.println("Error processing the input file given in JSON");
            e.printStackTrace();
        }
    }
}
//input the file for each of the vehicles being added you just grab the dealership ID and create a new dealership
//need input validation for nulls
