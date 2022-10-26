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

import static org.junit.jupiter.api.Assertions.*;

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
    final List<Car> cars = new ArrayList<>(Arrays.asList(testCar, testCar2, testCar3, testCar4));

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
        assertEquals(Status.SUCCESS, testResponse);
        assertTrue(testDc.getAllCars().contains(testCar));

        // negative test, car will not be added
        Status testResponse2 = testDc.addCar(testCar4);
        assertEquals(Status.FAILURE, testResponse2);
        assertFalse(testDc.getAllCars().contains(testCar4));
    }

    // adds the list of cars to the dealership controller
    @Test
    void addCars() {
        List<Status> resultList = testDc.addCars(cars);

        // checks the dealer ids
        List<String> dealerIds = new ArrayList<>(Arrays.asList("dealerId", "dealerId2"));
        assertTrue(testDc.getDealershipIds().containsAll(dealerIds));

        // checks each car object
        List<Car> validCars = new ArrayList<>(Arrays.asList(testCar, testCar2, testCar3));
        assertTrue(testDc.getAllCars().containsAll(validCars));

        // not all cars were added since one was invalid
        assertFalse(testDc.getAllCars().containsAll(cars));

        // check the result list, note the last car should fail to add
        assertEquals(new ArrayList<>(Arrays.asList(
                Status.SUCCESS, Status.SUCCESS, Status.SUCCESS, Status.FAILURE
        )), resultList);
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
        assertEquals(myCars, testDc.getDealershipCars(myDealerId));
    }

    @Test
    void removeCar() {
        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // add more cars to ensure that both copies are removed
        testDc.addCars(cars);

        // remove testCar
        assertTrue(testDc.getAllCars().contains(testCar));
        testDc.removeCar(testCar.getDealership_id(), testCar.getVehicle_id());
        assertFalse(testDc.getAllCars().contains(testCar));

        // remove testCar2
        assertTrue(testDc.getAllCars().contains(testCar2));
        testDc.removeCar(testCar2.getDealership_id(), testCar2.getVehicle_id());
        assertFalse(testDc.getAllCars().contains(testCar2));

        // remove testCar3
        assertTrue(testDc.getAllCars().contains(testCar3));
        testDc.removeCar(testCar3.getDealership_id(), testCar3.getVehicle_id());
        assertFalse(testDc.getAllCars().contains(testCar3));

        // try to remove testCar4, note that it wasn't added
        assertFalse(testDc.getAllCars().contains(testCar4));
        testDc.removeCar(testCar4.getDealership_id(), testCar4.getVehicle_id());
        assertFalse(testDc.getAllCars().contains(testCar4));
    }

    @Test
    void setDealershipAcquireEnabled() {
        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // check default status
        assertTrue(testDc.getDealershipAcquireEnabled(testCar.getDealership_id()));
        assertTrue(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()));

        // set to false, verify
        testDc.setDealershipAcquireEnabled(testCar.getDealership_id(), false);
        assertFalse(testDc.getDealershipAcquireEnabled(testCar.getDealership_id()));

        // check that other dealers are not affected
        assertTrue(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()));

        // set other dealer to false, verify
        testDc.setDealershipAcquireEnabled(testCar3.getDealership_id(), false);
        assertFalse(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()));

        // set to true, verify
        testDc.setDealershipAcquireEnabled(testCar.getDealership_id(), true);
        assertTrue(testDc.getDealershipAcquireEnabled(testCar.getDealership_id()));

        // check that other dealer is not affected
        assertFalse(testDc.getDealershipAcquireEnabled(testCar3.getDealership_id()));
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
        assertEquals(myCars, testCarList);

        // try to get car list of unknown dealer
        testCarList = testDc.getDealershipCars(testCar4.getDealership_id());
        myCars = new ArrayList<>();
        assertEquals(myCars, testCarList);
    }

    @Test
    void getDealershipIds() {
        // try to get dealer ids when there are no dealers
        assertEquals(new ArrayList<String>(), testDc.getDealershipIds());

        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // get dealer ids, verify
        List<String> knownDealerIds = new ArrayList<>(Arrays.asList("dealerId", "dealerId2"));
        List<String> myDealerIds = testDc.getDealershipIds();

        knownDealerIds.sort(null);
        myDealerIds.sort(null);

        assertEquals(knownDealerIds, myDealerIds);
    }

    @Test
    void getAllCars() {
        // try to get cars when there are no cars
        assertEquals(new ArrayList<Car>(), testDc.getAllCars());

        // add initial cars to create corresponding dealers
        testDc.addCars(cars);

        // get cars, sort, and verify
        List<Car> validCars = new ArrayList<>(Arrays.asList(testCar, testCar2, testCar3));
        validCars.sort(null);
        List<Car> myCars = testDc.getAllCars();
        myCars.sort(null);
        assertEquals(validCars, myCars);
    }
}