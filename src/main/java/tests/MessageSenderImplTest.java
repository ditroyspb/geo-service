package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

class MessageSenderImplTest {

    @ParameterizedTest
    @MethodSource("source")
    public void sendTest(Map<String, String> headers, String expected) {
        //arrange

        String ipAddress = String.valueOf(headers.get(IP_ADDRESS_HEADER));

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ipAddress))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        //act
        String result = messageSender.send(headers);

        //assert
        assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("x-real-ip", GeoServiceImpl.MOSCOW_IP, "Отправлено сообщение: Добро пожаловать"),
                Arguments.of("x-real-ip", GeoServiceImpl.NEW_YORK_IP, "Отправлено сообщение: Welcome"),
                Arguments.of("x-real-ip", "112.25.221", "Отправлено сообщение: Welcome")
        );
    }

    @BeforeAll
    public static void startedTests() {
        System.out.println("Tests started");
    }

    @AfterAll
    public static void finishedTests() {
        System.out.println("Tests finished");
    }
}