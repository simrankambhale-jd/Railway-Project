package practice.irctc.IRCTC.DTO.Booking;

import lombok.*;
import practice.irctc.IRCTC.Entity.BookingStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long userId;
    private String PNR;
    private LocalDate journeyDate;
    private Long trainId;
    private Long trainScheduleId;
    private Long sourceStationId;
    private Long destinationStationId;
    private Double totalFare;
    private Long coachId;
    private BookingStatus status;
    private List<PassengerDto> passengers;
}
