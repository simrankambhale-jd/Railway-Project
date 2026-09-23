package practice.irctc.IRCTC.DTO.Booking;

import lombok.*;
import practice.irctc.IRCTC.Entity.BerthType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketPassenger {

    private String name;
    private Integer age;
    private String gender;

    private Integer seatNumber;
    private BerthType berthType;
}
