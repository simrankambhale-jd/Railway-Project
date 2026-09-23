package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practice.irctc.IRCTC.Entity.TrainCoach;

import java.util.List;

public interface TrainCoachRepo extends JpaRepository<TrainCoach,Long> {
   @Query("SELECT ts from TrainCoach ts where ts.trainSchedule.id=?1 ")
    List<TrainCoach> findByTrainScheduleId(Long id);
}
