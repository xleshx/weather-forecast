package company.challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Data
public class DataUnit {
    @JsonProperty("dt")
    @JsonDeserialize(using = CustomDateSerializer.class)
    private LocalDateTime dt;

    @JsonProperty("main")
    @Valid
    private Telemetry main;
}
