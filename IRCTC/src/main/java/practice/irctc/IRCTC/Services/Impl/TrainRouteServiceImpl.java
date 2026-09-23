package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.TrainRouteDto;
import practice.irctc.IRCTC.Entity.Stations;
import practice.irctc.IRCTC.Entity.Train;
import practice.irctc.IRCTC.Entity.TrainRoute;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.StationsRepo;
import practice.irctc.IRCTC.Repository.TrainRepo;
import practice.irctc.IRCTC.Repository.TrainRouteRepo;
import practice.irctc.IRCTC.Services.TrainRouteService;

import java.util.List;

@Service
public class TrainRouteServiceImpl implements TrainRouteService {

    private TrainRouteRepo trainRouteRepo;
    private ModelMapper modelMapper;
    private TrainRepo trainRepo;
    private StationsRepo stationsRepo;

    public TrainRouteServiceImpl(TrainRouteRepo trainRouteRepo,ModelMapper modelMapper, TrainRepo trainRepo, StationsRepo stationsRepo){
        this.trainRouteRepo=trainRouteRepo;
        this.modelMapper=modelMapper;
        this.trainRepo=trainRepo;
        this.stationsRepo=stationsRepo;
    }

    @Override
    public List<TrainRouteDto> createRoutes(List<TrainRouteDto> dtoList){
        List<TrainRoute> routes= dtoList.stream().map(route->modelMapper.map(route,TrainRoute.class)).toList();
        for (TrainRouteDto trainRouteDto1 : dtoList) {
            Train train = trainRepo.findById(trainRouteDto1.getTrain().getId()).orElseThrow(() -> new ResourceNotFoundException("Train with Id not found"));
            Stations station = stationsRepo.findById(trainRouteDto1.getStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found"));
            TrainRoute trainRoute = modelMapper.map(trainRouteDto1, TrainRoute.class);
            trainRoute.setTrain(train);
            trainRoute.setStation(station);
        }
        List<TrainRoute> trainRoute1=trainRouteRepo.saveAll(routes);
        return trainRoute1.stream().map(route->modelMapper.map(route,TrainRouteDto.class)).toList();
    }

    @Override
    public void deleteRouteById(Long id){
        trainRouteRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route not found"));
    }

    @Override
    public TrainRouteDto updateRoute(Long id , TrainRouteDto trainRouteDto){
        TrainRoute trainRoute=trainRouteRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route not found"));
        trainRoute.setDistance(trainRouteDto.getDistance());
        trainRoute.setHaltMinutes(trainRouteDto.getHaltMinutes());
        trainRoute.setArrivalTime(trainRouteDto.getArrivalTime());
        trainRoute.setStationOrder(trainRouteDto.getStationOrder());
        trainRoute.setDepartureTime(trainRouteDto.getDepartureTime());

        Train train= trainRepo.findById(trainRouteDto.getTrain().getId()).orElseThrow(()->new ResourceNotFoundException("Train id not found"));
        Stations station=stationsRepo.findById(trainRouteDto.getStation().getId()).orElseThrow(()->new ResourceNotFoundException("Station not found "));
        trainRoute.setTrain(train);
        trainRoute.setStation(station);
        trainRouteRepo.save(trainRoute);
        return modelMapper.map(trainRoute,TrainRouteDto.class);
    }

    @Override
    public List<TrainRouteDto> getRouteByTrain(Long id){
       List<TrainRoute> trainRoutes= trainRouteRepo.findByTrainId(id);
       return trainRoutes.stream().map(route->modelMapper.map(route,TrainRouteDto.class)).toList();
    }

    @Override
    public List<TrainRouteDto> getAllRoutes(){
       List<TrainRoute> routesall = trainRouteRepo.findAll();
       return routesall.stream().map(route->modelMapper.map(route,TrainRouteDto.class)).toList();
    }

    @Override
    public TrainRouteDto getRouteById(Long id){
       TrainRoute route= trainRouteRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route id not found"));
       return modelMapper.map(route,TrainRouteDto.class);
    }
}
