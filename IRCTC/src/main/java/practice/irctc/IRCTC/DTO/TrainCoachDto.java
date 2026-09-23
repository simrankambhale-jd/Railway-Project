package practice.irctc.IRCTC.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import practice.irctc.IRCTC.Entity.CoachType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainCoachDto {

    private Long id;
    private TrainScheduleDto trainSchedule;
    private Integer totalSeats;
    private CoachType coach;
    private Integer availableSeats;
    private Double price;
    private Long coachNumber;
}
