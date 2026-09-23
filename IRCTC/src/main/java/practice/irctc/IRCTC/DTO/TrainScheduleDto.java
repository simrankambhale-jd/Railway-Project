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
public class TrainScheduleDto {

    private Long id;
    private TrainDto trainId;
    private LocalDate rundate;
    private Integer availableSeats;
}
