package company.challenge;

import company.challenge.client.OpenWeatherMapClient;
import company.challenge.domain.DataUnit;
import company.challenge.domain.OpenWeatherForecast;
import company.challenge.domain.Telemetry;
import company.challenge.dto.ForecastDTO;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

import static java.time.Clock.fixed;
import static java.time.Instant.parse;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.junit.Assert.assertEquals;

public class OpenWeatherServiceTest {
    private Clock fixedClock = fixed(parse("2018-09-23T12:00:00Z"), UTC);

    @Test
    public void testGetNoDailyForecast() {

        OpenWeatherForecast forecast = new OpenWeatherForecast();
        updateForecast(forecast, getInstant(1, 6), 10, 200);

        OpenWeatherMapClient openWeatherMapClient = (query, APIKey, cnt) -> forecast;
        OpenWeatherService service = new OpenWeatherService(fixedClock, openWeatherMapClient);

        ForecastDTO result = service.getForecast("Berlin");
        assertEquals(0, result.getThreeDaysDailyAverage(), 0.1);

    }

    @Test
    public void testGetOneDayDailyForecast() {
        OpenWeatherForecast forecast = new OpenWeatherForecast();
        updateForecast(forecast, getInstantFromDayStart(1, 9), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 14), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 17), 10, 200);

        OpenWeatherMapClient openWeatherMapClient = (query, APIKey, cnt) -> forecast;
        OpenWeatherService service = new OpenWeatherService(fixedClock, openWeatherMapClient);

        ForecastDTO result = service.getForecast("Berlin");
        assertEquals( 10, result.getThreeDaysDailyAverage(), 0.1);
    }

    @Test
    public void testGetAllThreeDaysDailyForecast() {
        OpenWeatherForecast forecast = new OpenWeatherForecast();
        updateForecast(forecast, getInstantFromDayStart(1, 9), 1, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 14), 5, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 17), 2, 200);

        updateForecast(forecast, getInstantFromDayStart(2, 9), 3, 200);
        updateForecast(forecast, getInstantFromDayStart(2, 14), 2, 200);
        updateForecast(forecast, getInstantFromDayStart(2, 17), 3, 200);

        updateForecast(forecast, getInstantFromDayStart(3, 10), 3, 200);
        updateForecast(forecast, getInstantFromDayStart(3, 15), 5, 200);

        OpenWeatherMapClient openWeatherMapClient = (query, APIKey, cnt) -> forecast;
        OpenWeatherService service = new OpenWeatherService(fixedClock, openWeatherMapClient);

        ForecastDTO result = service.getForecast("Berlin");
        assertEquals( 3, result.getThreeDaysDailyAverage(), 0.1);
    }

    @Test
    public void testGetOneDayNightlyForecast() {
        OpenWeatherForecast forecast = new OpenWeatherForecast();
        updateForecast(forecast, getInstantFromDayStart(1, 19), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 21), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(2, 4), 10, 200);

        OpenWeatherMapClient openWeatherMapClient = (query, APIKey, cnt) -> forecast;
        OpenWeatherService service = new OpenWeatherService(fixedClock, openWeatherMapClient);

        ForecastDTO result = service.getForecast("Berlin");
        assertEquals( 10, result.getThreeDaysNightlyAverage(), 0.1);
    }

    @Test
    public void testGetThreeDaysNightlyForecast() {
        OpenWeatherForecast forecast = new OpenWeatherForecast();
        updateForecast(forecast, getInstantFromDayStart(1, 19), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 21), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(2, 5), 20, 200);

        updateForecast(forecast, getInstantFromDayStart(2, 19), 20, 200);
        updateForecast(forecast, getInstantFromDayStart(2, 21), 20, 200);
        updateForecast(forecast, getInstantFromDayStart(3, 3), 10, 200);

        updateForecast(forecast, getInstantFromDayStart(3, 19), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(3, 20), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(4, 2), 10, 200);

        OpenWeatherMapClient openWeatherMapClient = (query, APIKey, cnt) -> forecast;
        OpenWeatherService service = new OpenWeatherService(fixedClock, openWeatherMapClient);

        ForecastDTO result = service.getForecast("Berlin");
        assertEquals( 13.3, result.getThreeDaysNightlyAverage(), 0.1);
    }

    @Test
    public void testGetOneDayPressureForecast() {
        OpenWeatherForecast forecast = new OpenWeatherForecast();
        updateForecast(forecast, getInstantFromDayStart(1, 19), 10, 200);
        updateForecast(forecast, getInstantFromDayStart(1, 21), 10, 300);
        updateForecast(forecast, getInstantFromDayStart(2, 4), 10, 400);

        OpenWeatherMapClient openWeatherMapClient = (query, APIKey, cnt) -> forecast;
        OpenWeatherService service = new OpenWeatherService(fixedClock, openWeatherMapClient);

        ForecastDTO result = service.getForecast("Berlin");
        assertEquals( 300, result.getThreeDaysPressureAverage(), 0.1);
    }

    private OpenWeatherForecast updateForecast(OpenWeatherForecast forecast, Instant dateTime,
                                               double temp, double pressure) {
        DataUnit unit = new DataUnit();
        unit.setDt(dateTime);
        Telemetry telemetry = new Telemetry(temp, pressure);
        unit.setMain(telemetry);
        forecast.getList().add(unit);
        return forecast;
    }

    private Instant getInstant(int plusDays, int plusHours) {
        return LocalDateTime.now(fixedClock)
                .plus(plusDays, DAYS)
                .plus(plusHours, HOURS)
                .truncatedTo(HOURS)
                .toInstant(UTC);

    }

    private Instant getInstantFromDayStart(int plusDays, int plusHours) {
        return LocalDateTime.now(fixedClock)
                .plus(plusDays, DAYS)
                .truncatedTo(DAYS)
                .plus(plusHours, HOURS)
                .truncatedTo(HOURS)
                .toInstant(UTC);

    }
}