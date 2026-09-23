package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.UserDto;
import practice.irctc.IRCTC.Entity.Role;
import practice.irctc.IRCTC.Entity.User;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.RoleRepo;
import practice.irctc.IRCTC.Repository.UserRepo;
import practice.irctc.IRCTC.Services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserRepo userRepo;
    private RoleRepo roleRepo;

    public UserServiceImpl(ModelMapper modelMapper,PasswordEncoder passwordEncoder,UserRepo userRepo,RoleRepo roleRepo){
        this.modelMapper=modelMapper;
        this.passwordEncoder=passwordEncoder;
        this.roleRepo=roleRepo;
        this.userRepo=userRepo;
    }

    @Override
    public  UserDto registerUser(UserDto userDto){
        User user=modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        Role roles=roleRepo.findByName("NORMAL").orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        user.getRoles().add(roles);
        User user1=userRepo.save(user);
        return modelMapper.map(user1, UserDto.class);
   }
}
