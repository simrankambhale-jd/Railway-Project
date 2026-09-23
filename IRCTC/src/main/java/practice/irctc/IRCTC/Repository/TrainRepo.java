package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.irctc.IRCTC.Entity.Train;

import java.util.List;

public interface TrainRepo extends JpaRepository<Train,Long> {

    @Query("SELECT tr.train from TrainRoute tr  WHERE tr.station.id= :sourceStationId OR tr.station.id= :destinationStationId")
    List<Train> findTrainBySourceAndDestination(@Param("sourceStationId") Long sourceStationId, @Param("destinationStationId") Long destinationStationId);
}
