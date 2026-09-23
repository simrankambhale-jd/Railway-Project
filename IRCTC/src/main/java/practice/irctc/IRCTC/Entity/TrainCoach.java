package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TrainCoach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TrainSchedule trainSchedule;

    private Integer totalSeats;

    @Enumerated(value=EnumType.STRING)
    private CoachType coach;

    private Double price;

    private Integer availableSeats;

    private String coachNumber;
}
