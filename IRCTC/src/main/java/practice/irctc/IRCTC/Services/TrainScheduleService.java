package practice.irctc.IRCTC.Services;

import org.springframework.web.bind.annotation.PathVariable;
import practice.irctc.IRCTC.DTO.TrainRouteDto;
import practice.irctc.IRCTC.DTO.TrainScheduleDto;

import java.util.List;

public interface TrainScheduleService {

    public TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto);

    public void deleteById(Long id);

    public TrainScheduleDto updateSchedule(Long id, TrainScheduleDto trainScheduleDto);

    public List<TrainScheduleDto> getAllSchedule();

    public TrainScheduleDto getScheduleById(Long id);

    public List<TrainScheduleDto> getScheduleByTrainId( Long trainid);
}
