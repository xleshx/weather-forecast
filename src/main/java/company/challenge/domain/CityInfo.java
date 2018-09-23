package company.challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CityInfo {
    @JsonProperty("country")
    private String countryCode;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
