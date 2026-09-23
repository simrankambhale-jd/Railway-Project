package practice.irctc.IRCTC.DTO.Booking;

import lombok.*;
import practice.irctc.IRCTC.DTO.StationsDto;
import practice.irctc.IRCTC.DTO.TrainCoachDto;
import practice.irctc.IRCTC.DTO.TrainScheduleDto;
import practice.irctc.IRCTC.DTO.UserDto;
import practice.irctc.IRCTC.Entity.BookingStatus;
import practice.irctc.IRCTC.Entity.Passenger;
import practice.irctc.IRCTC.Entity.Train;
import practice.irctc.IRCTC.Entity.TrainCoach;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    private Long id;

    private String pnr;

    private UserDto user;

    private TrainScheduleDto trainSchedule;

    private Train train;

    private StationsDto sourceStation;

    private StationsDto destinationStation;

    private LocalDate journeyDate;

    private Double totalFare;

    private BookingStatus status;

    private List<Passenger> passengers;

    private TrainCoachDto coach;
}
