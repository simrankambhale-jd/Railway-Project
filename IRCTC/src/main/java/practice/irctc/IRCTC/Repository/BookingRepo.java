package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practice.irctc.IRCTC.Entity.Booking;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = ?1")
    List<Booking> findByUser(Long userId);

    @Query("SELECT b FROM Booking b WHERE b.pnr= ?1")
    Booking findByPnr(String pnr);
}
