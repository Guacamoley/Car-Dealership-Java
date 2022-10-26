package DealershipSystem.Test;

import DealershipSystem.Car;
import DealershipSystem.Dealership;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test class is used to test some methods of the Dealership class
 */
class DealershipTest {

    // fields used for testing
    Dealership testDealership;
    Car testCar;

    // assign fields before each test
    @BeforeEach
    void setUp() {
        testDealership = new Dealership("dealerId");
        testCar = new Car("dealerId", "carId");
    }

    // clear fields after each test
    @AfterEach
    void tearDown() {
        testDealership = null;
        testCar = null;
    }

    // adds the car to the dealership, then asserts that the dealership has the car
    @Test
    void addCar() {
        testDealership.addCar(testCar);
        assertTrue(testDealership.getCars().get(0).equals(testCar));
    }

    // asserts that the dealership has a car that was added
    @Test
    void getCars() {
        testDealership.addCar(testCar);
        assertTrue(testDealership.getCars().get(0).equals(testCar));
    }

    // asserts that dealership can acquire by default
    @Test
    void isAcquireEnabled() {
        assertTrue(testDealership.isAcquireEnabled());
    }

    // asserts that acquisition can be set to either true or false
    @Test
    void setAcquireEnabled() {
        testDealership.setAcquireEnabled(false);
        assertTrue(!testDealership.isAcquireEnabled());
        testDealership.setAcquireEnabled(true);
        assertTrue(testDealership.isAcquireEnabled());
    }
}