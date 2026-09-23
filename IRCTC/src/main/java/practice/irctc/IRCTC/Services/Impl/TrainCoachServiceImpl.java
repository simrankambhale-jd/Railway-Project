package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.TrainCoachDto;
import practice.irctc.IRCTC.DTO.TrainRouteDto;
import practice.irctc.IRCTC.DTO.TrainScheduleDto;
import practice.irctc.IRCTC.Entity.TrainRoute;
import practice.irctc.IRCTC.Entity.TrainSchedule;
import practice.irctc.IRCTC.Entity.TrainCoach;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.TrainCoachRepo;
import practice.irctc.IRCTC.Repository.TrainScheduleRepo;
import practice.irctc.IRCTC.Services.TrainCoachService;

import java.util.List;

@Service
public class TrainCoachServiceImpl implements TrainCoachService {

    private TrainScheduleRepo trainScheduleRepo;
    private TrainCoachRepo trainCoachRepo;
    private ModelMapper modelMapper;

    public TrainCoachServiceImpl(TrainScheduleRepo trainScheduleRepo, TrainCoachRepo trainCoachRepo, ModelMapper modelMapper){
        this.modelMapper=modelMapper;
        this.trainScheduleRepo=trainScheduleRepo;
        this.trainCoachRepo = trainCoachRepo;
    }

    @Override
    public List<TrainCoachDto> createCoach(List<TrainCoachDto> coachList){
        List<TrainCoach> coaches= coachList.stream().map(coach->modelMapper.map(coach,TrainCoach.class)).toList();
        for(TrainCoachDto coach:coachList){
            TrainSchedule schedule=trainScheduleRepo.findById(coach.getTrainSchedule().getId()).orElseThrow(()->new ResourceNotFoundException("Train schedule not found"));
           TrainCoach coach1= modelMapper.map(coach, TrainCoach.class);
            coach1.setTrainSchedule(schedule);
        }
        List<TrainCoach> coachList1=trainCoachRepo.saveAll(coaches);
        return coachList1.stream().map(coach->modelMapper.map(coach,TrainCoachDto.class)).toList();
    }

    @Override
    public void deleteById( Long id){
        trainCoachRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Coach not found"));
    }

    @Override
    public TrainCoachDto updateCoach(Long id , TrainCoachDto trainCoachDto){
        TrainCoach seat= trainCoachRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seats not found"));
        seat.setTotalSeats(trainCoachDto.getTotalSeats());
        seat.setCoach(trainCoachDto.getCoach());
        TrainSchedule schedule=trainScheduleRepo.findById(trainCoachDto.getTrainSchedule().getId()).orElseThrow(()->new ResourceNotFoundException("Train with Schedule not found"));
        seat.setTrainSchedule(schedule);
        return modelMapper.map( trainCoachRepo.save(seat), TrainCoachDto.class);
    }

    @Override
    public List<TrainCoachDto> getAllCoach(){
        List<TrainCoach> seats= trainCoachRepo.findAll();
        return seats.stream().map(seat-> modelMapper.map(seat, TrainCoachDto.class)).toList();
    }

    @Override
    public TrainCoachDto getCoachById(Long id){
        TrainCoach seat= trainCoachRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seats not found"));
        return modelMapper.map(seat, TrainCoachDto.class);
    }

    @Override
    public List<TrainCoachDto> getCoachByScheduleId(Long scheduleid){
        List<TrainCoach> seats= trainCoachRepo.findByTrainScheduleId(scheduleid);
        return seats.stream().map(seat-> modelMapper.map(seat, TrainCoachDto.class)).toList();
    }
}
