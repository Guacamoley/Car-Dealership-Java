package DealershipSystem.Test;

import DealershipSystem.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test class is used to test some methods of the Car class.
 */
class CarTest {

    // fields used for testing
    final String testString = "test name";
    final String testString2 = "test name different";
    Car testCar;

    // assign fields before each test
    @BeforeEach
    void setUp() {
        testCar = new Car("dealerId", "carId");
    }

    // clear fields after each test
    @AfterEach
    void tearDown() {
        testCar = null;
    }

    // asserts loaned field matches the value that was set
    @Test
    void getLoaned() {
        testCar.setLoaned(true);
        assertTrue(testCar.getLoaned());
    }

    // asserts loaned field can be set to a given boolean
    @Test
    void setLoaned() {
        testCar.setLoaned(true);
        assertTrue(testCar.getLoaned());
        testCar.setLoaned(false);
        assertTrue(!testCar.getLoaned());
    }

    // asserts dealer name matches the string that was set
    @Test
    void getDealerName() {
        testCar.setDealerName(testString);
        assertTrue(testCar.getDealerName().equals(testString));
    }

    // asserts dealer name can be set to a given string
    @Test
    void setDealerName() {
        testCar.setDealerName(testString);
        assertTrue(testCar.getDealerName().equals(testString));
        testCar.setDealerName(testString2);
        assertTrue(testCar.getDealerName().equals(testString2));
    }
}