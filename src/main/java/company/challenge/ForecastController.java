package company.challenge;

import company.challenge.dto.ForecastDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
public class ForecastController {

    private final OpenWeatherService service;

    @Autowired
    public ForecastController(OpenWeatherService service) {
        this.service = service;
    }

    @RequestMapping(method = GET, path = "/data", params = "city", produces = APPLICATION_JSON_VALUE)
    public ForecastDTO get(@RequestParam String city) {
        log.info("Getting forecast for {}", city);
        return service.getForecast(city, "de");
    }


}
