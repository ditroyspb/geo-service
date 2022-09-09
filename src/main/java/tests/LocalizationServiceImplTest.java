package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    @BeforeAll
    public static void startedTests() {
        System.out.println("Tests started");
    }

    @AfterAll
    public static void finishedTests() {
        System.out.println("Tests finished");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void localeTest(Country country, String expected) {
        //arrange
        LocalizationService localizationService = new LocalizationServiceImpl();
        //act
        String result = localizationService.locale(country);
        //assert
        assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }

}