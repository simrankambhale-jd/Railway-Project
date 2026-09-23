package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.irctc.IRCTC.Entity.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger,Long> {
}
