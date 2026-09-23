package practice.irctc.IRCTC.Services;

import practice.irctc.IRCTC.DTO.TrainSeatDto;

import java.util.List;

public interface TrainSeatService {
    List<TrainSeatDto> createSeats(List<TrainSeatDto> dto);

    TrainSeatDto getSeatById(Long id);

    List<TrainSeatDto> getAllSeats();

    List<TrainSeatDto> getSeatsByCoach(Long coachId);


    TrainSeatDto updateSeat(Long id, TrainSeatDto dto);

    void deleteSeat(Long id);

     List<TrainSeatDto> searchSeatsByCoach(Long trainScheduleId,Long coachId);
}
