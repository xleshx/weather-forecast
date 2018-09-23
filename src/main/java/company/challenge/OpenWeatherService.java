package company.challenge;

import company.challenge.client.OpenWeatherMapClient;
import company.challenge.domain.DataUnit;
import company.challenge.domain.OpenWeatherForecast;
import company.challenge.domain.Telemetry;
import company.challenge.dto.ForecastDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
class OpenWeatherService {

    @Value("${api.key:MOCK_KEY}")
    private String apiKey;

    private final Clock clock;

    private final OpenWeatherMapClient openWeatherMapClient;

    @Autowired
    public OpenWeatherService(Clock clock, OpenWeatherMapClient openWeatherMapClient) {
        this.clock = clock;
        this.openWeatherMapClient = openWeatherMapClient;
    }

    public ForecastDTO getForecast(String city) {
        OpenWeatherForecast forecast = openWeatherMapClient.getForecast(city, apiKey, valueOf(32));
        log.info("Getting forecast for {}, {}", forecast.getCityInfo().getName(),
                forecast.getCityInfo().getCountryCode());

        return getForecastDTO(forecast.getList());
    }

    private ForecastDTO getForecastDTO(List<DataUnit> dataUnits) {
        ForecastDTO result = new ForecastDTO();

        result.setThreeDaysDailyAverage(calculate3DaysDailyAverage(dataUnits));
        result.setThreeDaysNightlyAverage(calculate3DaysNightlyAverage(dataUnits));
        result.setThreeDaysPressureAverage(calculate3DaysPressureAverage(dataUnits));
        return result;
    }

    private double calculate3DaysDailyAverage(List<DataUnit> dataUnits) {
        return Stream.of(1, 2, 3)
                .flatMapToDouble(val -> calculateDailyAverage(dataUnits, val,
                        LocalTime.of(6, 0, 0, 0), LocalTime.of(18, 0, 0, 0),
                        false, this::getTempValueSafe))
                .average().orElse(0);
    }

    private double calculate3DaysNightlyAverage(List<DataUnit> dataUnits) {
        return Stream.of(1, 2, 3)
                .flatMapToDouble(val -> calculateDailyAverage(dataUnits, val,
                        LocalTime.of(18, 0, 0, 0), LocalTime.of(6, 0, 0, 0),
                        true, this::getTempValueSafe))
                .average().orElse(0);
    }

    private double calculate3DaysPressureAverage(List<DataUnit> dataUnits) {
        return Stream.of(1, 2, 3)
                .flatMapToDouble(val -> calculateDailyAverage(dataUnits, val,
                        LocalTime.of(0, 0, 0, 0), LocalTime.of(23, 59, 59, 999),
                        false, this::getPressureSafe))
                .average().orElse(0);
    }

    private DoubleStream calculateDailyAverage(List<DataUnit> dataUnits, int dayCount,
                                               LocalTime dailyStart, LocalTime dailyEnd, boolean endNextDay,
                                               Function<DataUnit, Double> operation) {

        Instant start = getRangedInstant(dayCount, dailyStart);
        Instant end = getRangedInstant(endNextDay ? dayCount + 1 : dayCount, dailyEnd);

        return dataUnits.stream()
                .filter(unit -> isInstantWithin(start, end, unit.getDt()))
                .mapToDouble(operation::apply);
    }

    private Instant getRangedInstant(int dayCount, LocalTime dailyStart) {
        return LocalDate.now(clock)
                .plus(dayCount, ChronoUnit.DAYS).atTime(dailyStart).toInstant(ZoneOffset.UTC);
    }

    private boolean isInstantWithin(Instant start, Instant end, Instant toCheck) {
        return toCheck.isAfter(start) && toCheck.isBefore(end);
    }

    private double getTempValueSafe(DataUnit unit) {
        return ofNullable(unit.getMain()).map(Telemetry::getTemp).orElse(0D);
    }

    private double getPressureSafe(DataUnit unit) {
        return ofNullable(unit.getMain()).map(Telemetry::getPressure).orElse(0D);
    }
}
