package practice.irctc.IRCTC.DTO.Booking;


import lombok.*;
import practice.irctc.IRCTC.Entity.BerthType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDto {

        private Long id;
        private Long seatId;
        private String name;
        private Integer age;
        private String gender;
        private BerthType berthPreference;
}
