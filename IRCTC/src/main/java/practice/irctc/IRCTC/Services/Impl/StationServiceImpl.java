package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.StationsDto;
import practice.irctc.IRCTC.Entity.Stations;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.StationsRepo;
import practice.irctc.IRCTC.Services.StationService;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private StationsRepo stationsRepo;
    private ModelMapper modelMapper;

    public StationServiceImpl(StationsRepo stationsRepo, ModelMapper modelMapper){
        this.stationsRepo=stationsRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<StationsDto> createStations(List<StationsDto> stationsDto) {
        List<Stations> stations = stationsDto.stream().map(dto -> modelMapper.map(dto, Stations.class)).toList();
        List<Stations> savedStations = stationsRepo.saveAll(stations);
        return savedStations.stream().map(station -> modelMapper.map(station, StationsDto.class)).toList();
    }

    @Override
    public void deleteById(Long id){
       Stations station= stationsRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Station not found"));
       stationsRepo.delete(station);
    }

    @Override
    public StationsDto updateStation(Long id, StationsDto stationsDto){
      Stations stations=  stationsRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Station mot found"));
      stations.setName(stationsDto.getName());
      stations.setCode(stationsDto.getCode());
      stations.setCity(stationsDto.getCity());
      stations.setState(stationsDto.getState());
      stationsRepo.save(stations);
      return modelMapper.map(stations,StationsDto.class);

    }

    @Override
    public List<StationsDto> getAllStations(){
       List<Stations> stationsAll= stationsRepo.findAll();
       return stationsAll.stream().map(stations->modelMapper.map(stations,StationsDto.class)).toList();
    }

    @Override
    public StationsDto getById(Long id){
       Stations stations= stationsRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Station not found"));
       return modelMapper.map(stations,StationsDto.class);
    }
}

