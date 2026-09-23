package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.AvailableTrainResponse;
import practice.irctc.IRCTC.DTO.CoachAvailabilityDto;
import practice.irctc.IRCTC.DTO.TrainDto;
import practice.irctc.IRCTC.DTO.UserTrainSearchRequest;
import practice.irctc.IRCTC.Entity.*;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.StationsRepo;
import practice.irctc.IRCTC.Repository.TrainCoachRepo;
import practice.irctc.IRCTC.Repository.TrainRepo;
import practice.irctc.IRCTC.Services.TrainService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService {

    private TrainRepo trainRepo;
    private ModelMapper modelMapper;
    private StationsRepo stationsRepo;
    private TrainCoachRepo coachRepo;

    public TrainServiceImpl(TrainRepo trainRepo,ModelMapper modelMapper,StationsRepo stationsRepo,TrainCoachRepo coachRepo){
        this.trainRepo=trainRepo;
        this.modelMapper=modelMapper;
        this.stationsRepo=stationsRepo;
        this.coachRepo=coachRepo;
    }

    @Override
    public TrainDto createTrain(TrainDto trainDto){
        Stations sourceStation=stationsRepo.findById(trainDto.getSourceStation().getId()).orElseThrow(()->new ResourceNotFoundException("Source Station not found"));
        Stations destinationStation=stationsRepo.findById(trainDto.getDestinationStation().getId()).orElseThrow(()->new ResourceNotFoundException("Destination Station not found"));

        Train trains =modelMapper.map(trainDto,Train.class);

        trains.setSourceStationId(sourceStation);
        trains.setDestinationStationId(destinationStation);
        Train train = trainRepo.save(trains);
        return modelMapper.map(train,TrainDto.class);
    }

    @Override
    public void deleteTrain(Long id ){
        Train train=trainRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Train not found with Id :" + id));
        trainRepo.delete(train);
    }

    @Override
    public TrainDto updateTrain(TrainDto train,Long id){
        Train trains= trainRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Train with ID is not found" + id));
       trains.setTrainNo(train.getTrainNo());
       trains.setName(train.getName());
       trains.setDistance(train.getDistance());

       Stations sourceStation=stationsRepo.findById(train.getSourceStation().getId()).orElseThrow(()->new ResourceNotFoundException("Station not found"));
       trains.setSourceStationId(sourceStation);

       Stations destinationStation=stationsRepo.findById(train.getSourceStation().getId()).orElseThrow(()->new ResourceNotFoundException("Station not found"));;
       trains.setDestinationStationId(destinationStation);

       Train updatedtrain= trainRepo.save(trains);
       return modelMapper.map(updatedtrain, TrainDto.class);
    }

    @Override
    public List<TrainDto> getAllTrains(){
        List<Train> alltrain=  trainRepo.findAll();
        return alltrain.stream().map(train->(modelMapper.map(train,TrainDto.class))).toList();
    }

    @Override
    public TrainDto getTrainById(Long id){
        Train train=trainRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Train not found"));
        return modelMapper.map(train, TrainDto.class);
    }

    @Override
    public List<AvailableTrainResponse> searchTrain(UserTrainSearchRequest request){
        List<Train> matchedTrains=this.trainRepo.findTrainBySourceAndDestination(request.getSourceStationId(),
                request.getDestinationStationId());

        List<AvailableTrainResponse> trainResponses= matchedTrains.stream().filter(train-> {
            Integer sourceStationOrder = null;
            Integer destinationStationOrder = null;

            for (TrainRoute trainRoute : train.getRoutes()) {
                if (trainRoute.getStation().getId().equals(request.getSourceStationId())) {
                    sourceStationOrder = trainRoute.getStationOrder();
                } else if (trainRoute.getStation().getId().equals(request.getDestinationStationId())) {
                    destinationStationOrder = trainRoute.getStationOrder();
                }

                if (sourceStationOrder != null && destinationStationOrder != null) {
                    break;
                }
            }
            boolean validOrder = sourceStationOrder != null && destinationStationOrder != null && sourceStationOrder < destinationStationOrder;
            boolean runOnThatDay = train.getSchedules().stream().anyMatch(
                    schedule -> schedule.getRundate().equals(request.getJourneyDate()));

            return validOrder && runOnThatDay;
        })
                .map(train->{
            TrainSchedule trainSchedule=train.getSchedules().stream()
                    .filter(trainSchedule1 -> trainSchedule1.getRundate().equals(request.getJourneyDate()))
                    .findFirst().orElse(null);

            if(trainSchedule==null){
                return null;
            }

      TrainRoute sourceRoute=train.getRoutes().stream()
              .filter(route-> route.getStation().getId().equals(request.getSourceStationId()))
              .findFirst().orElse(null);

      if (sourceRoute ==null){
          return null;
      }
      TrainRoute destinationRoute=train.getRoutes().stream()
              .filter(route-> route.getStation().getId().equals(request.getDestinationStationId()))
              .findFirst().orElse(null);

      if(destinationRoute==null){
          return null;
      }

      List<TrainCoach> coachList=coachRepo.findByTrainScheduleId(trainSchedule.getId());

      List<CoachAvailabilityDto> coachAvailabilityDtos=coachList.stream()
              .map(coach->CoachAvailabilityDto.builder()
                      .id(coach.getId())
                      .coach(coach.getCoach())
                      .availableSeats(coach.getAvailableSeats())
                      .price(coach.getPrice())
                      .coachNumber(coach.getCoachNumber())
                      .build()).toList();


            return AvailableTrainResponse.builder()
                    .trainId(trainSchedule.getTrainId().getId())
                    .trainName(trainSchedule.getTrainId().getName())
                    .trainNo(trainSchedule.getTrainId().getTrainNo())
                    .scheduleId(trainSchedule.getId())
                    .journeyDate(trainSchedule.getRundate())
                    .arrivalTime(sourceRoute.getDepartureTime())
                    .departureTime(destinationRoute.getDepartureTime())
                    .sourceStationId(sourceRoute.getId())
                    .sourceStationName(sourceRoute.getStation().getName())
                    .destinationStationId(destinationRoute.getId())
                    .destinationStationName(destinationRoute.getStation().getName())
                    .coaches(coachAvailabilityDtos)
                    .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return trainResponses;
    }
}
