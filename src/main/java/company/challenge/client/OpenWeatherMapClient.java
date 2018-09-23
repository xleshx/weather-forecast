package company.challenge.client;

import company.challenge.domain.OpenWeatherForecast;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient("open-weather-map")
public interface OpenWeatherMapClient {
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"data/2.5/forecast"},
//            value = {"http://api.openweathermap.org/data/2.5/forecast"},
            produces = {"application/json"}
    )
    OpenWeatherForecast getForecast(@RequestParam(value="q") Set<String> query,
                                    @RequestParam(value="APPID") String APIKey);

}
