package practice.irctc.IRCTC.Config.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import practice.irctc.IRCTC.Entity.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private User user;

    public CustomUserDetail(User user){
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> list=user
                .getRoles()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getName()))
                .toList();
        return list;
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getEmail();
    }
}
