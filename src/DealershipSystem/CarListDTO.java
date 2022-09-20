package DealershipSystem;

import java.util.List;

public class CarListDTO {
	
	private List<Car> car_inventory;
	
	public List<Car> getCarInventory(){
		return car_inventory;
	}
	
	public void setText(List<Car> carInventory) {
		this.car_inventory = carInventory;
	}
}
