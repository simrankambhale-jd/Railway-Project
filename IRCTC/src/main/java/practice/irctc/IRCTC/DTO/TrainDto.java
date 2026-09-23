package practice.irctc.IRCTC.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainDto {

    private Long id;
    private String trainNo;
    private String name;
    private String distance;
    private StationsDto SourceStation;
    private StationsDto DestinationStation;
}
