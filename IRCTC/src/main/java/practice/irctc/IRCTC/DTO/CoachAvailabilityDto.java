package practice.irctc.IRCTC.DTO;

import lombok.*;
import practice.irctc.IRCTC.Entity.CoachType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachAvailabilityDto {

    private Long id;
    private CoachType coach;
    private String coachNumber;
    private Integer availableSeats;
    private Double price;

}
