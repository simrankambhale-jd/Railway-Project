package practice.irctc.IRCTC.DTO.Booking;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    private Long userId;
    private Long trainId;
    private Long trainScheduleId;
    private Long sourceStationId;
    private Long destinationStationId;
    private Long coachId;
    private List<PassengerDto> passengers;
}
