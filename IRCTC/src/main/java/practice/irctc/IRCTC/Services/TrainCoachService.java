package practice.irctc.IRCTC.Services;

import practice.irctc.IRCTC.DTO.TrainCoachDto;

import java.util.List;

public interface TrainCoachService {

    public List<TrainCoachDto> createCoach(List<TrainCoachDto> trainCoachDto);

    public void deleteById( Long id);

    public TrainCoachDto updateCoach(Long id , TrainCoachDto trainCoachDto);

    public List<TrainCoachDto> getAllCoach();

    public TrainCoachDto getCoachById(Long id);

    public List<TrainCoachDto> getCoachByScheduleId(Long scheduleid);
}
