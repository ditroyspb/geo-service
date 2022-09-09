package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import org.mockito.Mockito;
import java.util.stream.Stream;

class GeoServiceImplTest {

    GeoServiceImpl sut;

    @BeforeEach
    public void initTest() {
        System.out.println("Test started");
    }

    @AfterEach
    public void finishTest() {
        System.out.println("Test finished");
    }

    @BeforeAll
    public static void startedTests() {
        System.out.println("Tests started");
    }

    @AfterAll
    public static void finishedTests() {
        System.out.println("Tests finished");
    }

    @Test
    public void byCoordinatesTest() {
        //arrange
        double a = 1.132, b = 1.341231;
        var expected = RuntimeException.class;

        //assert
        assertThrows(expected,
                () -> sut.byCoordinates(a, b));
    }

    @ParameterizedTest
    @MethodSource("source")
    public void byIPTest(String a, Location expected) {
        //arrange
        GeoService geoService = new GeoServiceImpl();
        //act
        Location result = geoService.byIp(a);
        //assert
        assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.1.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.1.1", new Location("New York", Country.USA, null,  0)),
                Arguments.of("76.1.1.1", null)
        );
    }
}