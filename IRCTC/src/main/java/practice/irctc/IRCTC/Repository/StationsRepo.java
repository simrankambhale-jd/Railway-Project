package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.irctc.IRCTC.Entity.Stations;

public interface StationsRepo extends JpaRepository<Stations,Long> {
}
