package practice.irctc.IRCTC.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTrainResponse {

    private Long trainId;
    private String trainNo;
    private String trainName;

    private Long scheduleId;

    private Long sourceStationId;
    private String sourceStationName;
    private Long destinationStationId;
    private String destinationStationName;

    private LocalDate journeyDate;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    private List<CoachAvailabilityDto> coaches;
}
