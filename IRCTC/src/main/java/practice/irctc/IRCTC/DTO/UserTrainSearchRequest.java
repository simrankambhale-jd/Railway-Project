package practice.irctc.IRCTC.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainSearchRequest {

    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDate journeyDate;
}
