package company.challenge.dto;

import lombok.Data;

@Data
public class ForecastDTO {
    double threeDaysDailyAverageMinorUnits;
    double threeDaysNightlyAverageMinorUnits;
    double threeDaysPressureAverageMinorUnits;
}
