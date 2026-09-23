package practice.irctc.IRCTC.Config.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.Entity.User;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("Username not found"));
        CustomUserDetail userDetail=new CustomUserDetail(user);
        return userDetail;
    }
}
