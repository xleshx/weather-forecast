package company.challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class OpenWeatherForecast {
    @Valid
    @JsonProperty("list")
    List<DataUnit> list =  new ArrayList<>();

}
