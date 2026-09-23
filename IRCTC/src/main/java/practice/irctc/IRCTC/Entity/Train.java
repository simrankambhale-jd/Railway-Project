package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainNo;
    private String name;
    private String distance;

    @ManyToOne
    @JoinColumn(name="sourceId")
    private Stations SourceStationId;

    @ManyToOne
    @JoinColumn(name="destinationId")
    private Stations DestinationStationId;

    @OneToMany(mappedBy="train")
    private List<TrainRoute> routes;

     @OneToMany(mappedBy="trainId")
    private List<TrainSchedule> Schedules;

}
