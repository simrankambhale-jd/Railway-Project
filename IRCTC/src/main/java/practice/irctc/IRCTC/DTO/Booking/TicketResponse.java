package practice.irctc.IRCTC.DTO.Booking;

import lombok.*;
import practice.irctc.IRCTC.Entity.BookingStatus;
import practice.irctc.IRCTC.Entity.CoachType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private Long id;
    private String userName;
    private String PNR;
    private LocalDate journeyDate;
    private String trainName;
    private String trainCode;
    private String sourceStationName;
    private String destinationStationName;
    private Double totalFare;
    private String coach;
    private CoachType coachType;
    private BookingStatus status;
    private List<TicketPassenger> passengers;
}
