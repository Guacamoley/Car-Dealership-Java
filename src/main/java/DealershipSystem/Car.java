package DealershipSystem;

/**
 * The car class defines the car object that we use to create the cars for use in the dealerships.
 **/
public class Car { //CLASS DIAGRAM IS PUT IN AS "VEHICLE"
    private String dealership_id;
    private String vehicle_type;
    private String vehicle_manufacturer;
    private String vehicle_model;
    private String vehicle_id;
    private Double price;
    private long acquisition_date;

    public Car(String dealership_id, String vehicle_type, String vehicle_manufacturer, String vehicle_model, String vehicle_id, Double price, long acquisition_date) {
        this.dealership_id = dealership_id;
        this.vehicle_type = vehicle_type;
        this.vehicle_manufacturer = vehicle_manufacturer;
        this.vehicle_model = vehicle_model;
        this.vehicle_id = vehicle_id;
        this.price = price;
        this.acquisition_date = acquisition_date;
    }

    public Car(String dealership_id, String vehicle_id) {
        this.dealership_id = dealership_id;
        this.vehicle_id = vehicle_id;
    }


    /**
     * getDealership_id will return the dealership_id from the car object, so it can be assigned to its corresponding dealership.
     **/

    public String getDealership_id() {
        return dealership_id;
    }

    /**
     * setter for the dealership_id.
     **/

    public void setDealership_id(String dealership_id) {
        this.dealership_id = dealership_id;
    }

    /**
     * returns the vehicle type from the car object.
     **/

    public String getVehicle_type() {
        return vehicle_type;
    }

    /**
     * setter for the vehicle type.
     **/

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    /**
     * returns the manufacturer's name of the car object.
     **/
    public String getVehicle_manufacturer() {
        return vehicle_manufacturer;
    }

    /**
     * setter for the vehicle manufacturer.
     **/
    public void setVehicle_manufacturer(String vehicle_manufacturer) {
        this.vehicle_manufacturer = vehicle_manufacturer;
    }

    /**
     * will return the model of the car object.
     **/
    public String getVehicle_model() {
        return vehicle_model;
    }

    /**
     * setter for the model of the vehicle.
     **/
    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    /**
     * returns the vehicle id of the car object.
     **/

    public String getVehicle_id() {
        return vehicle_id;
    }


    /**
     * will set the vehicle id of the car object.
     **/
    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    /**
     * returns the price of the vehicle stored in the car object.
     **/
    public Double getPrice() {
        return price;
    }

    /**
     * setter for the vehicle price.
     **/
    public void setPrice(Double price) {
        this.price = price;
    }


    /**
     * returns the date of acquisition of the car stored in the car object.
     **/
    public long getAcquisition_date() {
        return acquisition_date;
    }


    /**
     * setter for the acquisition date.
     **/
    public void setAcquisition_date(long acquisition_date) {
        this.acquisition_date = acquisition_date;
    }


    /**
     * toString for the car object. This method is used to list the vehicles in the gui.
     **/
    @Override
    public String toString() {
        return "Dealership ID: " + dealership_id + '\n' + "Vehicle Type: " + vehicle_type + '\n' + "Vehicle Manufacturer: " + vehicle_manufacturer + '\n' + "Vehicle Model: " + vehicle_model + '\n' + "Vehicle ID: " + vehicle_id + '\n' + "Price: " + price + '\n' + "Acquisition Date: " + acquisition_date + '\n';
    }
}


