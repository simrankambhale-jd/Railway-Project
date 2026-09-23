package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.irctc.IRCTC.Entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

   Optional<User> findByEmail(String email);
}
