package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practice.irctc.IRCTC.Entity.TrainSchedule;
import practice.irctc.IRCTC.Entity.TrainSeat;

import java.util.List;

public interface TrainSeatRepo  extends JpaRepository<TrainSeat, Long> {

    List<TrainSeat> findByCoachId(Long coachId);

    @Query(" SELECT ts FROM TrainSeat ts WHERE ts.trainScheduleId.id = ?1 AND ts.coach.id = ?2")
    List<TrainSeat> findByTrainScheduleIdIdAndCoachId(Long trainScheduleId,Long coachId);


}
