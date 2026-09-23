package practice.irctc.IRCTC.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import practice.irctc.IRCTC.DTO.Auth.LoginRequest;
import practice.irctc.IRCTC.DTO.UserDto;

public interface UserService {

    UserDto registerUser(UserDto userDto);

}
