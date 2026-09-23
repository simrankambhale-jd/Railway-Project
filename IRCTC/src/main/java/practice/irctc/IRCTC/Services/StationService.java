package practice.irctc.IRCTC.Services;

import practice.irctc.IRCTC.DTO.StationsDto;

import java.util.List;

public interface StationService  {


    public List<StationsDto> createStations(List<StationsDto> stationsDto);

    public void deleteById(Long id);

    public StationsDto updateStation(Long id, StationsDto stationsDto);

    public List<StationsDto> getAllStations();

    public StationsDto getById(Long id);
}
