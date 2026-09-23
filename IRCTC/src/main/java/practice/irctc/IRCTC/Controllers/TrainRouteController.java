package practice.irctc.IRCTC.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.TrainDto;
import practice.irctc.IRCTC.DTO.TrainRouteDto;
import practice.irctc.IRCTC.Services.TrainRouteService;
import practice.irctc.IRCTC.Services.TrainService;

import java.util.List;

@RestController
@RequestMapping("/admin/train/routes")
public class TrainRouteController {

    private TrainRouteService trainRouteService;

    public TrainRouteController(TrainRouteService trainRouteService){
        this.trainRouteService=trainRouteService;
    }

    @PostMapping
    public ResponseEntity<List<TrainRouteDto>> create(@RequestBody List<TrainRouteDto> trainRouteDto){
        return new ResponseEntity<>(trainRouteService.createRoutes(trainRouteDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        trainRouteService.deleteRouteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainRouteDto> update(@PathVariable Long id , @RequestBody TrainRouteDto trainRouteDto){
        return new ResponseEntity<>(trainRouteService.updateRoute(id,trainRouteDto),HttpStatus.OK);
    }

    @GetMapping("/train/{trainId}")
    public List<TrainRouteDto> getByTrain(@PathVariable Long trainId){
        return trainRouteService.getRouteByTrain(trainId);
    }

    @GetMapping
    public List<TrainRouteDto> getAll(){
        return trainRouteService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public TrainRouteDto getById(@PathVariable Long id){
        return trainRouteService.getRouteById(id);
    }
}
