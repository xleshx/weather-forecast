package company.challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Telemetry {
    @JsonProperty("temp")
    private double temp;
    @JsonProperty("pressure")
    private double pressure;
}
