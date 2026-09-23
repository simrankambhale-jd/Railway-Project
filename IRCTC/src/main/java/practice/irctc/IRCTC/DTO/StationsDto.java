package practice.irctc.IRCTC.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationsDto {

    private Long id;
    private String code;
    private String name;
    private String city;
    private String state;
}
