package company.challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Telemetry {
    @JsonProperty("temp")
    private double temp;
    @JsonProperty("pressure")
    private double pressure;
}
