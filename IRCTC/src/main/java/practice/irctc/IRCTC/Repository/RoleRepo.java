package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.irctc.IRCTC.Entity.Role;

import java.util.Optional;

public interface RoleRepo  extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
