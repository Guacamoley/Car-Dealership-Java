

public class Car {
    String dealership_id;
    String vehicle_type;
    String vehicle_manufacturer;
    String vehicle_model;
    String vehicle_id;
    Double price; //Determine the right data type for this and date below
    long acquisition_date;


    public Car(String dealership_id, String vehicle_type, String vehicle_manufacturer, String vehicle_model, String vehicle_id, Double price, long acquisition_date) {
        this.dealership_id = dealership_id;
        this.vehicle_type = vehicle_type;
        this.vehicle_manufacturer = vehicle_manufacturer;
        this.vehicle_model = vehicle_model;
        this.vehicle_id = vehicle_id;
        this.price = price;
        this.acquisition_date = acquisition_date;
    }

    public String getDealership_id() {
        return dealership_id;
    }

    public void setDealership_id(String dealership_id) {
        this.dealership_id = dealership_id;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_manufacturer() {
        return vehicle_manufacturer;
    }

    public void setVehicle_manufacturer(String vehicle_manufacturer) {
        this.vehicle_manufacturer = vehicle_manufacturer;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getAcquisition_date() {
        return acquisition_date;
    }

    public void setAcquisition_date(long acquisition_date) {
        this.acquisition_date = acquisition_date;
    }

    @Override
    public String toString() {
        return "DealershipSystem.Car{" +
                "dealership_id='" + dealership_id + '\'' +
                ", vehicle_type='" + vehicle_type + '\'' +
                ", vehicle_manufacturer='" + vehicle_manufacturer + '\'' +
                ", vehicle_model='" + vehicle_model + '\'' +
                ", vehicle_id='" + vehicle_id + '\'' +
                ", price=" + price +
                ", acquisition_date=" + acquisition_date +
                '}';
    }
}
