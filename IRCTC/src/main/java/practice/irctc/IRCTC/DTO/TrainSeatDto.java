package practice.irctc.IRCTC.DTO;

import practice.irctc.IRCTC.Entity.BerthType;
import lombok.*;
import practice.irctc.IRCTC.Entity.TrainSchedule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSeatDto {

    private Long id;

    private Integer seatNumber;

    private Long trainScheduleId;

    private BerthType berthType;

    private Double price;

    private Boolean available;

    private Long coachId;
}
