package practice.irctc.IRCTC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practice.irctc.IRCTC.Entity.TrainRoute;

import java.util.List;

public interface TrainRouteRepo extends JpaRepository<TrainRoute,Long> {

    @Query("SELECT tr FROM TrainRoute tr WHERE tr.train.id = ?1 order by tr.stationOrder")
    public List<TrainRoute> findByTrainId(Long trainId);

}
