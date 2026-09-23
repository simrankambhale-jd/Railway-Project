package practice.irctc.IRCTC.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainRouteDto {

    private Long id;
    private TrainDto train;
    private StationsDto station;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Integer haltMinutes;
    private Integer distance;
    private Integer stationOrder;
}
