package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.TrainScheduleDto;
import practice.irctc.IRCTC.Entity.Train;
import practice.irctc.IRCTC.Entity.TrainSchedule;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.TrainRepo;
import practice.irctc.IRCTC.Repository.TrainScheduleRepo;
import practice.irctc.IRCTC.Services.TrainScheduleService;

import java.util.List;

@Service
public class TrainScheduleServiceImpl implements TrainScheduleService {

    private TrainScheduleRepo trainScheduleRepo;
    private TrainRepo trainRepo;
    private ModelMapper modelMapper;

    public TrainScheduleServiceImpl(TrainScheduleRepo trainScheduleRepo,TrainRepo trainRepo,ModelMapper modelMapper){
        this.trainScheduleRepo=trainScheduleRepo;
        this.trainRepo=trainRepo;
        this.modelMapper=modelMapper;
    }
    @Override
    public TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto){
        TrainSchedule trainSchedule=modelMapper.map(trainScheduleDto,TrainSchedule.class);
        Train train=trainRepo.findById(trainScheduleDto.getTrainId().getId()).orElseThrow(()->new ResourceNotFoundException("Train not found"));
        trainSchedule.setTrainId(train);
        return modelMapper.map( trainScheduleRepo.save(trainSchedule),TrainScheduleDto.class);
    }

    @Override
    public void deleteById(Long id){
        trainScheduleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Schedule not found"));
    }

    @Override
    public TrainScheduleDto updateSchedule(Long id, TrainScheduleDto trainScheduleDto){
        TrainSchedule trainSchedule=trainScheduleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Schedule not found"));
        trainSchedule.setRundate(trainScheduleDto.getRundate());
        trainSchedule.setAvailableSeats(trainScheduleDto.getAvailableSeats());
        Train train=trainRepo.findById(trainScheduleDto.getTrainId().getId()).orElseThrow(()->new ResourceNotFoundException("Train not found"));
        trainSchedule.setTrainId(train);
        return modelMapper.map( trainScheduleRepo.save(trainSchedule),TrainScheduleDto.class);
    }

    @Override
    public List<TrainScheduleDto> getAllSchedule(){
        List<TrainSchedule> schedules=trainScheduleRepo.findAll();
        return schedules.stream().map(schedule-> modelMapper.map(schedule,TrainScheduleDto.class)).toList();
    }

    @Override
    public TrainScheduleDto getScheduleById(Long id){
        TrainSchedule trainSchedule=trainScheduleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Schedule not found"));
        return modelMapper.map(trainSchedule,TrainScheduleDto.class);
    }

   @Override
    public List<TrainScheduleDto> getScheduleByTrainId( Long trainid){
     List<TrainSchedule> schedules=trainScheduleRepo.findByTrainIdId(trainid);
    return schedules.stream().map(schedule->modelMapper.map(schedule,TrainScheduleDto.class)).toList();
   }
}
