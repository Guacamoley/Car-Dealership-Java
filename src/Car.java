
    /*
    * TODO:
    * Attributes:
    *   -vehicleID
    *   -manufacturer
    *   -model
    *   -acquisitionDate
    *   -price
    *   -vehicleType
    *   -dealershipID
    *
    * Methods:
    *   +getters() & setters()
    *   +toString()
    * */

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
            return "Car{" +
                    "dealership_id='" + dealership_id + '\n' +
                    ", vehicle_type='" + vehicle_type + '\n' +
                    ", vehicle_manufacturer='" + vehicle_manufacturer + '\n' +
                    ", vehicle_model='" + vehicle_model + '\n' +
                    ", vehicle_id='" + vehicle_id + '\n' +
                    ", price=" + price + '\n' +
                    ", acquisition_date=" + acquisition_date + '\n' +
                    '}';
        }
    }


