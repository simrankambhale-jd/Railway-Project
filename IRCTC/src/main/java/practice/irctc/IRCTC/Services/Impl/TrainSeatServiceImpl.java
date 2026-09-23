package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.TrainSeatDto;
import practice.irctc.IRCTC.Entity.TrainCoach;
import practice.irctc.IRCTC.Entity.TrainSchedule;
import practice.irctc.IRCTC.Entity.TrainSeat;
import practice.irctc.IRCTC.Repository.TrainCoachRepo;
import practice.irctc.IRCTC.Repository.TrainScheduleRepo;
import practice.irctc.IRCTC.Repository.TrainSeatRepo;
import practice.irctc.IRCTC.Services.TrainSeatService;

import java.util.List;

@Service
public class TrainSeatServiceImpl implements TrainSeatService {

    private final TrainSeatRepo trainSeatRepo;
    private final TrainCoachRepo coachRepo;
    private final ModelMapper modelMapper;
    private final TrainScheduleRepo trainScheduleRepo;

    public TrainSeatServiceImpl(TrainSeatRepo trainSeatRepo,TrainCoachRepo coachRepo ,ModelMapper modelMapper, TrainScheduleRepo trainScheduleRepo) {
        this.modelMapper = modelMapper;
        this.trainSeatRepo=trainSeatRepo;
        this.coachRepo=coachRepo;
        this.trainScheduleRepo=trainScheduleRepo;
    }

    @Override
    public List<TrainSeatDto> createSeats(List<TrainSeatDto> dtoList) {
        List<TrainSeat> seats = dtoList.stream().map(dto -> {
            TrainSeat seat = modelMapper.map(dto, TrainSeat.class);
            // Handle Coach
            if (dto.getCoachId() != null) {
                TrainCoach coach = coachRepo.findById(dto.getCoachId()).orElseThrow(() -> new RuntimeException("Coach not found with id: " + dto.getCoachId()));
                seat.setCoach(coach);
            }
            // Handle TrainSchedule
            if (dto.getTrainScheduleId() != null) {
                TrainSchedule trainSchedule = trainScheduleRepo.findById(dto.getTrainScheduleId()).orElseThrow(() -> new RuntimeException("Train schedule not found with id: " + dto.getTrainScheduleId()));
                seat.setTrainScheduleId(trainSchedule);
            }
            return seat;}).toList();
        List<TrainSeat> savedSeats = trainSeatRepo.saveAll(seats);
        return savedSeats.stream().map(seat -> {
            TrainSeatDto dto = modelMapper.map(seat, TrainSeatDto.class);
            if (seat.getCoach() != null) {
                dto.setCoachId(seat.getCoach().getId());
            }
            if (seat.getTrainScheduleId() != null) {
                dto.setTrainScheduleId(seat.getTrainScheduleId().getId());
            }
            return dto;
        }).toList();
    }

    @Override
    public TrainSeatDto getSeatById(Long id) {
        TrainSeat seat = trainSeatRepo.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));
        TrainSeatDto dto = modelMapper.map(seat, TrainSeatDto.class);
        if (seat.getCoach() != null) {
            dto.setCoachId(seat.getCoach().getId());
        }
        if (seat.getTrainScheduleId() != null) {
            dto.setTrainScheduleId(seat.getTrainScheduleId().getId());
        }
        return dto;
    }

    @Override
    public List<TrainSeatDto> getAllSeats() {
        return trainSeatRepo.findAll().stream().map(seat -> {
                    TrainSeatDto dto = modelMapper.map(seat, TrainSeatDto.class);
                    if (seat.getCoach() != null) {
                        dto.setCoachId(seat.getCoach().getId());
                    }
                    if (seat.getTrainScheduleId() != null) {
                        dto.setTrainScheduleId(seat.getTrainScheduleId().getId());
                    }
                    return dto;}).toList();
    }

    @Override
    public List<TrainSeatDto> getSeatsByCoach(Long coachId) {
        return trainSeatRepo.findByCoachId(coachId).stream().map(seat -> {
                    TrainSeatDto dto = modelMapper.map(seat, TrainSeatDto.class);
                    dto.setCoachId(coachId);
                    if (seat.getTrainScheduleId() != null) {
                        dto.setTrainScheduleId(seat.getTrainScheduleId().getId());
                    }
                    return dto;}).toList();
    }

    @Override
    public TrainSeatDto updateSeat(Long id, TrainSeatDto dto) {
        TrainSeat seat = trainSeatRepo.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));
        // Mapnormal fields
        modelMapper.map(dto, seat);
        // Handle Coach separately
        if (dto.getCoachId() != null) {
            TrainCoach coach = coachRepo.findById(dto.getCoachId()).orElseThrow(() -> new RuntimeException("Coach not found with id: " + dto.getCoachId()));
            seat.setCoach(coach);
        }
        // Handle TrainSchedule separately
        if (dto.getTrainScheduleId() != null) {
            TrainSchedule trainSchedule = trainScheduleRepo.findById(dto.getTrainScheduleId()).orElseThrow(() -> new RuntimeException("Train schedule not found with id: " + dto.getTrainScheduleId()));
            seat.setTrainScheduleId(trainSchedule);
        }
        TrainSeat updatedSeat = trainSeatRepo.save(seat);
        TrainSeatDto responseDto = modelMapper.map(updatedSeat, TrainSeatDto.class);
        if (updatedSeat.getCoach() != null) {
            responseDto.setCoachId(updatedSeat.getCoach().getId());
        }
        if (updatedSeat.getTrainScheduleId() != null) {
            responseDto.setTrainScheduleId(updatedSeat.getTrainScheduleId().getId());
        }
        return responseDto;
    }

    @Override
    public void deleteSeat(Long id) {
        if (!trainSeatRepo.existsById(id)) {
            throw new RuntimeException("Seat not found");
        }
        trainSeatRepo.deleteById(id);
    }

        @Override
        public List<TrainSeatDto> searchSeatsByCoach(Long trainScheduleId,Long coachId){
          List<TrainSeat> seatList= trainSeatRepo.findByTrainScheduleIdIdAndCoachId(trainScheduleId,coachId);
           return seatList.stream().map(seat->modelMapper.map(seat,TrainSeatDto.class)).toList();
        }
    }
