package practice.irctc.IRCTC.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.StationsDto;
import practice.irctc.IRCTC.Services.StationService;

import java.util.List;

@RestController
@RequestMapping("/admin/train/station")
public class StationController {

    private StationService stationService;

    public StationController(StationService stationService){
        this.stationService=stationService;
    }

    @PostMapping
    public ResponseEntity<List<StationsDto>> create(@RequestBody List<StationsDto> stations) {
        return new ResponseEntity<>(stationService.createStations(stations), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        stationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<StationsDto> update(@PathVariable Long id ,@RequestBody StationsDto stationDto){
        return new ResponseEntity<>(stationService.updateStation(id,stationDto),HttpStatus.OK);
    }

    @GetMapping
    public List<StationsDto> getAll(){
        return stationService.getAllStations();
    }

    @GetMapping("/{id}")
    public StationsDto getById(@PathVariable Long id){
        return stationService.getById(id);
    }
}
