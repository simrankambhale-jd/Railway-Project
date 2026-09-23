package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.*;
import practice.irctc.IRCTC.DTO.TrainScheduleDto;

@Entity
@Table(name = "train_seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private BerthType berthType;

    @ManyToOne
    private TrainSchedule trainScheduleId;

    private Double price;

    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", nullable = false)
    private TrainCoach coach;
}
