package company.challenge.client;

import company.challenge.domain.OpenWeatherForecast;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("open-weather-map")
public interface OpenWeatherMapClient {
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"data/2.5/forecast?units=metric"},
            produces = {"application/json"}
    )
    OpenWeatherForecast getForecast(@RequestParam(value="q") String city,
                                    @RequestParam(value="APPID") String APIKey,
                                    @RequestParam(value="cnt") String cnt);

}
