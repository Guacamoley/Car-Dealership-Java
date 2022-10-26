package DealershipSystem.Test;

import DealershipSystem.Car;
import DealershipSystem.DealershipController;
import DealershipSystem.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests all methods of the dealership controller class.
 */
class DealershipControllerTest {

    // fields for testing
    DealershipController testDc;

    // two cars from same dealer
    final Car testCar = new Car("dealerId", "vehicleId");
    final Car testCar2 = new Car("dealerId", "vehicleId2");

    // car from different dealer
    final Car testCar3 = new Car("dealerId2", "vehicleId3");

    // invalid car
    final Car testCar4 = new Car(null, "vehicleId4");

    // list of all cars
    final List<Car> cars = new ArrayList(Arrays.asList(testCar, testCar2, testCar3, testCar4));

    // create the test objects before each test
    @BeforeEach
    void setUp() {
        testDc = new DealershipController();
    }

    // clear the test objects after each test
    @AfterEach
    void tearDown() {
        testDc = null;
    }

    // test adding car, assert that the returned status matches, and assert car is in the car list
    @Test
    void addCar() {
        // positive test, car will be added
        Status testResponse = testDc.addCar(testCar);
        assertEquals(testResponse, Status.SUCCESS);
        assertEquals(testDc.getAllCars().contains(testCar), true);

        // negative test, car will not be added
        Status testResponse2 = testDc.addCar(testCar4);
        assertEquals(testResponse2, Status.FAILURE);
        assertEquals(testDc.getAllCars().contains(testCar4), false);
    }

    // adds the list of cars to the dealership controller
    @Test
    void addCars() {
        List<Status> resultList = testDc.addCars(cars);

        // checks the dealer ids
        List<String> dealerIds = new ArrayList<>(Arrays.asList("dealerId", "dealerId2"));
        assertEquals(testDc.getDealershipIds().containsAll(dealerIds), true);

        // checks each car object
        List<Car> validCars = new ArrayList<>(Arrays.asList(testCar, testCar2, testCar3));
        assertEquals(testDc.getAllCars().containsAll(validCars), true);

        // not all cars were added since one was invalid
        assertEquals(testDc.getAllCars().containsAll(cars), false);

        // check the result list, note the last car should fail to add
        assertEquals(resultList, new ArrayList<>(Arrays.asList(
                Status.SUCCESS, Status.SUCCESS, Status.SUCCESS, Status.FAILURE
        )));
    }

    @Test
    void setDealershipCars() {
        // add initial car to create corresponding dealer
        testDc.addCar(testCar);

        // setup fields for a single dealer
        List<Car> myCars = new ArrayList<>(Arrays.asList(testCar, testCar2));
        String myDealerId = testCar.getDealership_id();

        // assign car list to that dealer
        testDc.setDealershipCars(myCars, myDealerId);
        assertEquals(testDc.getDealershipCars(myDealerId), myCars);
    }

    @Test
    void removeCar() {
        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // add more cars to ensure that both copies are removed
        testDc.addCars(cars);

        // remove testCar
        assertEquals(testDc.getAllCars().contains(testCar), true);
        testDc.removeCar(testCar.getDealership_id(), testCar.getVehicle_id());
        assertEquals(testDc.getAllCars().contains(testCar), false);

        // remove testCar2
        assertEquals(testDc.getAllCars().contains(testCar2), true);
        testDc.removeCar(testCar2.getDealership_id(), testCar2.getVehicle_id());
        assertEquals(testDc.getAllCars().contains(testCar2), false);

        // remove testCar3
        assertEquals(testDc.getAllCars().contains(testCar3), true);
        testDc.removeCar(testCar3.getDealership_id(), testCar3.getVehicle_id());
        assertEquals(testDc.getAllCars().contains(testCar3), false);

        // try to remove testCar4, note that it wasn't added
        assertEquals(testDc.getAllCars().contains(testCar4), false);
        testDc.removeCar(testCar4.getDealership_id(), testCar4.getVehicle_id());
        assertEquals(testDc.getAllCars().contains(testCar4), false);
    }

    @Test
    void setDealershipAcquireEnabled() {
        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // check default status
        assertEquals(testDc.getDealershipAcquireEnabled(testCar.getDealership_id()), true);
        assertEquals(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()), true);

        // set to false, verify
        testDc.setDealershipAcquireEnabled(testCar.getDealership_id(), false);
        assertEquals(testDc.getDealershipAcquireEnabled(testCar.getDealership_id()), false);

        // check that other dealers are not affected
        assertEquals(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()), true);

        // set other dealer to false, verify
        testDc.setDealershipAcquireEnabled(testCar3.getDealership_id(), false);
        assertEquals(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()), false);

        // set to true, verify
        testDc.setDealershipAcquireEnabled(testCar.getDealership_id(), true);
        assertEquals(testDc.getDealershipAcquireEnabled(testCar.getDealership_id()), true);

        // check that other dealer is not affected
        assertEquals(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()), false);
    }

    @Test
    void getDealershipAcquireEnabled() {
        // getter/setter pair testing
        this.setDealershipAcquireEnabled();
    }

    @Test
    void getDealershipCars() {
        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // check car list against known dealer cars
        List<Car> testCarList = testDc.getDealershipCars(testCar.getDealership_id());
        List<Car> myCars = new ArrayList<>(Arrays.asList(testCar, testCar2));
        assertEquals(testCarList, myCars);

        // try to get car list of unknown dealer
        testCarList = testDc.getDealershipCars(testCar4.getDealership_id());
        myCars = new ArrayList<>();
        assertEquals(testCarList, myCars);
    }

    @Test
    void getDealershipIds() {
        // try to get dealer ids when there are no dealers
        assertEquals(testDc.getDealershipIds(), new ArrayList<String>());

        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // get dealer ids, verify
        List<String> knownDealerIds = new ArrayList<>(Arrays.asList("dealerId", "dealerId2"));
        List<String> myDealerIds = testDc.getDealershipIds();

        knownDealerIds.sort(null);
        myDealerIds.sort(null);

        assertEquals(myDealerIds, knownDealerIds);
    }

    @Test
    void getAllCars() {
        // try to get cars when there are no cars
        assertEquals(testDc.getAllCars(), new ArrayList<Car>());

        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // get cars, sort, and verify
        List<Car> validCars = new ArrayList<>(Arrays.asList(testCar, testCar2, testCar3));
        validCars.sort(null);
        List<Car> myCars = testDc.getAllCars();
        myCars.sort(null);
        assertEquals(myCars, validCars);
    }
}