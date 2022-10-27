package DealershipSystem.Test;

import DealershipSystem.Car;
import DealershipSystem.Inventory;
import DealershipSystem.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class tests the inventory class methods
 */
class InventoryTest {

    // fields for testing
    Inventory testInventory;
    final Car testCar = new Car("testDealerId", "testVehicleId");
    final Car testCar2 = new Car("testDealerId", "testVehicleId2");
    final Car testCar3 = new Car("testDealerId2", "testVehicleId3");
    final Car testCar4 = new Car(null, "testVehicleId4");

    // private helper method for testing loads a preset file
    private List<Status> loadFile(){
        return testInventory.importFile(new File("resources\\input.json"), "json");
    }

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
    }

    @AfterEach
    void tearDown() {
        testInventory = null;
    }

    @Test
    void importFile() {
        // import sample file
        List<Status> responseList = loadFile();

        // check responses
        assertEquals(4, responseList.size());
        assertEquals(Status.SUCCESS, responseList.get(0));
        assertEquals(Status.SUCCESS, responseList.get(3));

        // check dealers
        assertTrue(testInventory.isExistingDealer("77338"));
        assertFalse(testInventory.isExistingDealer("fake"));

        // check a car
        Car testCar = testInventory.getDealerCars("77338").get(0);
        assertEquals("229393", testCar.getVehicle_id());
    }

    @Test
    void getAllDealershipIds() {
        // check when there are no cars loaded
        List<String> myDealerIds = testInventory.getAllDealershipIds();
        assertEquals(new ArrayList<String>(), myDealerIds);

        // load cars
        loadFile();

        // get dealer ids, sort, verify
        List<String> knownDealerIds = new ArrayList<>(Arrays.asList("12513", "77338"));
        myDealerIds = testInventory.getAllDealershipIds();

        knownDealerIds.sort(null);
        myDealerIds.sort(null);

        assertEquals(knownDealerIds, myDealerIds);
    }

    @Test
    void addIncomingVehicle() {
        // add cars
        Status response1 = testInventory.addIncomingVehicle(testCar);
        Status response2 = testInventory.addIncomingVehicle(testCar2);
        Status response3 = testInventory.addIncomingVehicle(testCar3);
        Status response4 = testInventory.addIncomingVehicle(testCar4);

        // check status
        assertEquals(Status.SUCCESS, response1);
        assertEquals(Status.SUCCESS, response2);
        assertEquals(Status.SUCCESS, response3);
        assertEquals(Status.FAILURE, response4);

        // retrieve dealer's car list
        String dealerId = testCar.getDealership_id();
        List<Car> myCars = testInventory.getDealerCars(dealerId);
        myCars.sort(null);

        // check cars
        List<Car> knownCars = new ArrayList<>(Arrays.asList(testCar, testCar2));
        knownCars.sort(null);
        assertEquals(knownCars, myCars);

        // check all dealer ids
        List<String> myDealerIds = testInventory.getAllDealershipIds();
        myDealerIds.sort(null);
        assertEquals(new ArrayList<>(Arrays.asList("testDealerId", "testDealerId2")), myDealerIds);
    }

    @Test
    void removeIncomingVehicle() {
        // try to remove when there are no cars
        Status response = testInventory.removeIncomingVehicle(testCar);
        assertEquals(Status.FAILURE, response);

        // try to remove a null car
        response = testInventory.removeIncomingVehicle(null);
        assertEquals(Status.FAILURE, response);

        // load cars and validate one car based on the file info
        loadFile();
        Car loadedCar = testInventory.getDealerCars("12513").get(1);
        assertEquals("83883", loadedCar.getVehicle_id());

        // confirm car is in dealer list
        List<Car> dealerCars = testInventory.getDealerCars(loadedCar.getDealership_id());
        assertTrue(dealerCars.contains(loadedCar));

        // remove car and verify removal from dealer list
        response = testInventory.removeIncomingVehicle(loadedCar);
        assertEquals(Status.SUCCESS, response);
        dealerCars = testInventory.getDealerCars(loadedCar.getDealership_id());
        assertFalse(dealerCars.contains(loadedCar));
    }

    @Test
    void getDealerAcquisition() {
        // check default acquisition
        loadFile();
        assertTrue(testInventory.getDealerAcquisition("12513"));
        assertTrue(testInventory.getDealerAcquisition("77338"));

        // change value, test again
        testInventory.disableDealerAcquisition("12513");
        assertFalse(testInventory.getDealerAcquisition("12513"));
        assertTrue(testInventory.getDealerAcquisition("77338"));
    }

    @Test
    void isExistingDealer() {
        // check before adding anything
        assertFalse(testInventory.isExistingDealer("fake"));

        // add cars, then check
        loadFile();
        assertFalse(testInventory.isExistingDealer("fake"));
        assertTrue(testInventory.isExistingDealer("12513"));
        assertTrue(testInventory.isExistingDealer("77338"));
    }

    @Test
    void enableDealerAcquisition() {
        // add cars
        loadFile();

        // count cars for each dealer
        assertEquals(3, testInventory.getDealerCars("12513").size());
        assertEquals(1, testInventory.getDealerCars("77338").size());

        // disable acquisition
        testInventory.disableDealerAcquisition("12513");

        // load more cars, count each dealer
        loadFile();
        assertEquals(3, testInventory.getDealerCars("12513").size());
        assertEquals(2, testInventory.getDealerCars("77338").size());

        // enable acquisition
        testInventory.enableDealerAcquisition("12513");

        // load more cars, count each dealer
        loadFile();
        assertEquals(6, testInventory.getDealerCars("12513").size());
        assertEquals(3, testInventory.getDealerCars("77338").size());
    }

    @Test
    void disableDealerAcquisition() {
        // pairwise testing for complimentary method
        this.enableDealerAcquisition();
    }

    @Test
    void exportFile() {
        // load cars, validate dealer list sizes
        loadFile();
        assertEquals(3, testInventory.getDealerCars("12513").size());
        assertEquals(1, testInventory.getDealerCars("77338").size());

        // export, then import the file, validate dealer list sizes
        testInventory.exportFile("12513", "resources\\testOutput.json");
        testInventory.importFile(new File("resources\\testOutput.json"), "json");
        assertEquals(6, testInventory.getDealerCars("12513").size());
        assertEquals(1, testInventory.getDealerCars("77338").size());
    }

    @Test
    void exportSession() {
        // load cars, validate dealer list sizes
        loadFile();
        assertEquals(3, testInventory.getDealerCars("12513").size());
        assertEquals(1, testInventory.getDealerCars("77338").size());

        // export, then import the file, validate dealer list sizes
        testInventory.exportSession("resources\\session.json");
        testInventory.importFile(new File("resources\\session.json"), "json");
        assertEquals(6, testInventory.getDealerCars("12513").size());
        assertEquals(2, testInventory.getDealerCars("77338").size());
    }

    @Test
    void getDealerCars() {
        // check when there are no cars
        assertEquals(0, testInventory.getDealerCars("12513").size());
        assertEquals(0, testInventory.getDealerCars("fake").size());

        // load cars, then check
        loadFile();
        assertEquals(3, testInventory.getDealerCars("12513").size());
        assertEquals(0, testInventory.getDealerCars("fake").size());

        // validate a car
        Car myCar = testInventory.getDealerCars("12513").get(1);
        assertEquals("83883", myCar.getVehicle_id());
    }

    @Test
    void printCars() {
        // check when there are no cars
        assertEquals("", testInventory.printCars("testDealerId", "\n"));

        // add cars
        testInventory.addIncomingVehicle(testCar);
        testInventory.addIncomingVehicle(testCar2);
        testInventory.addIncomingVehicle(testCar3);

        // create expected result, which is only the first 2 cars
        final String expectedString = testCar + "\n" + testCar2 + "\n";

        // check
        assertEquals(expectedString, testInventory.printCars("testDealerId", "\n"));
    }
}