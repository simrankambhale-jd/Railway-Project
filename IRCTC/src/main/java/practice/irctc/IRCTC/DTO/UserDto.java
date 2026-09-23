package practice.irctc.IRCTC.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime createdAt;
    private List<RoleDto> roles=new ArrayList<>();
}
