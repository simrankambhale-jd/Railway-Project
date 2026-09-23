package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practice.irctc.IRCTC.Entity.TrainSchedule;

import java.util.List;

public interface TrainScheduleRepo extends JpaRepository<TrainSchedule,Long> {

    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.trainId.id = ?1 ")
    public List<TrainSchedule> findByTrainIdId(Long trainId);

}
