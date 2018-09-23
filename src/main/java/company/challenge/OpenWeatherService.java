package company.challenge;

import company.challenge.client.OpenWeatherMapClient;
import company.challenge.domain.OpenWeatherForecast;
import company.challenge.dto.ForecastDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

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

    public ForecastDTO getForecast(String city, String countryCode){
        OpenWeatherForecast forecast = openWeatherMapClient.getForecast(Set.of(city, countryCode), apiKey);

        ForecastDTO result = new ForecastDTO();
        result.setThreeDaysDailyAverageMinorUnits(forecast.getList().iterator().next().getMain().getTemp());
        result.setThreeDaysNightlyAverageMinorUnits(forecast.getList().iterator().next().getMain().getTemp());
        result.setThreeDaysPressureAverageMinorUnits(forecast.getList().iterator().next().getMain().getPressure());
        return result;
    }
}
