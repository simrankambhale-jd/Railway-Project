package practice.irctc.IRCTC.DTO.Auth;

import practice.irctc.IRCTC.DTO.UserDto;

public record JwtResponse(String token, String refreshToken, UserDto user) {
}
