package practice.irctc.IRCTC.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.TrainDto;
import practice.irctc.IRCTC.Services.TrainService;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainController {

    private TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping
    public ResponseEntity<TrainDto> createTrain(@RequestBody TrainDto trainDto){
        return new ResponseEntity<>(trainService.createTrain(trainDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainById(@PathVariable Long id){
       trainService.deleteTrain(id);
       return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<TrainDto> getAllTrains(){return trainService.getAllTrains();}

    @GetMapping("/{id}")
    public TrainDto getTrainById(@PathVariable Long id){return trainService.getTrainById(id);}

    @PutMapping("/{id}")
    public ResponseEntity<TrainDto> updateTrains(@RequestBody TrainDto train , @PathVariable Long id ){
     return new ResponseEntity<>(trainService.updateTrain(train,id),HttpStatus.OK);
    }
}
