package practice.irctc.IRCTC.Services;

import org.springframework.web.bind.annotation.RequestBody;
import practice.irctc.IRCTC.DTO.AvailableTrainResponse;
import practice.irctc.IRCTC.DTO.TrainDto;
import practice.irctc.IRCTC.DTO.UserTrainSearchRequest;

import java.util.List;

public interface TrainService {

    public TrainDto createTrain(TrainDto trainDto);

    public void deleteTrain(Long id);

    public TrainDto updateTrain(TrainDto train, Long trainId);

    public List<TrainDto> getAllTrains();

    public TrainDto getTrainById(Long id);

    public List<AvailableTrainResponse> searchTrain( UserTrainSearchRequest request);
}
