package practice.irctc.IRCTC.Services;

import practice.irctc.IRCTC.DTO.TrainRouteDto;

import java.util.List;

public interface TrainRouteService {

    public List<TrainRouteDto> createRoutes(List<TrainRouteDto> dtoList);

    public void deleteRouteById(Long id);

    public TrainRouteDto updateRoute(Long id, TrainRouteDto trainRouteDto);

    public List<TrainRouteDto> getAllRoutes();

    public TrainRouteDto getRouteById(Long id);

    public List<TrainRouteDto> getRouteByTrain(Long id);
}
