package company.challenge.dto;

import lombok.Data;

@Data
public class ForecastDTO {
    double threeDaysDailyAverage;
    double threeDaysNightlyAverage;
    double threeDaysPressureAverage;
}
