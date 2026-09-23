package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnr;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "train_schedule_id")
    private TrainSchedule trainSchedule;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "source_station_id")
    private Stations sourceStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id")
    private Stations destinationStation;

    private LocalDate journeyDate;

    private Double totalFare;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    private LocalDateTime bookedAt;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private TrainCoach coach;
}