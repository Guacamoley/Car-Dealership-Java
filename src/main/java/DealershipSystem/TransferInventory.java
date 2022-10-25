package DealershipSystem;

public class TransferInventory {

    /**
     * Transfers a car between two dealerships from user input.
     *
     * @param dealership1 The dealership to transfer a vehicle from.
     * @param dealership2 The dealership to transfer a vehicle to.
     * @param vehicleID The car that is getting transferred.
     * @param inventory The dealership inventory we are accessing for the transfer.
     * */
    public void transferCar(String dealership1, String dealership2, String vehicleID, Inventory inventory) {
        for (Car car : inventory.getDealerCars(dealership1)) {
            if (car.getVehicle_id().equalsIgnoreCase(vehicleID)) {
                car.setDealership_id(dealership2);
                inventory.addIncomingVehicle(car);
                inventory.getDealerCars(dealership1).remove(car);
                break;
            }
        }
    }
}
