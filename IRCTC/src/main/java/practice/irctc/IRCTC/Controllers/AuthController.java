package practice.irctc.IRCTC.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.irctc.IRCTC.Config.Security.JwtHelper;
import practice.irctc.IRCTC.DTO.Auth.ErrorResponse;
import practice.irctc.IRCTC.DTO.Auth.JwtResponse;
import practice.irctc.IRCTC.DTO.Auth.LoginRequest;
import practice.irctc.IRCTC.DTO.UserDto;
import practice.irctc.IRCTC.Entity.User;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.UserRepo;
import practice.irctc.IRCTC.Services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private UserDetailsService userDetailsService;
    private JwtHelper jwtHelper;
    private UserRepo userRepo;
    private ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;

    public AuthController(UserService userService,UserDetailsService userDetailsService,JwtHelper jwtHelper, UserRepo userRepo,ModelMapper modelMapper, AuthenticationManager authenticationManager){
        this.userService=userService;
        this.authenticationManager=authenticationManager;
        this.userRepo=userRepo;
        this.jwtHelper=jwtHelper;
        this.modelMapper=modelMapper;
        this.userDetailsService=userDetailsService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest login) {
        try {
            UsernamePasswordAuthenticationToken authentication = new
                    UsernamePasswordAuthenticationToken(login.username(), login.password());

            this.authenticationManager.authenticate(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(login.username());

            String token = jwtHelper.generateAccessToken(userDetails);
            String refresh_token = jwtHelper.generateRefreshToken(userDetails);
            User user = userRepo.findByEmail(login.username()).get();
            UserDto userDto = modelMapper.map(user, UserDto.class);

            JwtResponse response = new JwtResponse(token,refresh_token, userDto);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(BadCredentialsException exception){
            System.out.println("Invalid Credentials");
            ErrorResponse response=new ErrorResponse("Username and Password is incorrect" ," 403", true);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody(required = false) String refreshToken) {
        if (refreshToken == null) {
            return new ResponseEntity<>(new ErrorResponse("refresh token is null", "400", false), HttpStatus.BAD_REQUEST);
        }
        if (!jwtHelper.isRefreshToken(refreshToken)) {
            return new ResponseEntity<>(new ErrorResponse("The token you sent is not valid refresh token", "400", false), HttpStatus.BAD_REQUEST);
        }
        String usernameFromToken = jwtHelper.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
        if (jwtHelper.isTokenValid(refreshToken, userDetails)) {
            String accessToken = jwtHelper.generateAccessToken(userDetails);
            String newRefreshToken = jwtHelper.generateRefreshToken(userDetails);
            UserDto userDto = modelMapper.map(userRepo.findByEmail(usernameFromToken).orElse(null), UserDto.class);
            return new ResponseEntity<>(new JwtResponse(accessToken, newRefreshToken, userDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("refresh token is not valid", "400", false), HttpStatus.BAD_REQUEST);
        }
    }
}
