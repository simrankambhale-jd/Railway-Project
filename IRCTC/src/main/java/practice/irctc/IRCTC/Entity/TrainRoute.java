package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TrainRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="trainId")
    private Train train;

    @ManyToOne
    @JoinColumn(name="stationId")
    private Stations station;

    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Integer haltMinutes;
    private Integer distance;
    private Integer stationOrder;
}
