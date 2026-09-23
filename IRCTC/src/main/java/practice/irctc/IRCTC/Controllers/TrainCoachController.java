package practice.irctc.IRCTC.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.TrainCoachDto;
import practice.irctc.IRCTC.Services.TrainCoachService;

import java.util.List;

@RestController
@RequestMapping("/admin/train/coach")
public class TrainCoachController {

    private TrainCoachService trainCoachService;

    public TrainCoachController(TrainCoachService trainCoachService){
        this.trainCoachService = trainCoachService;
    }

    @PostMapping
    public ResponseEntity<List<TrainCoachDto>> create(@RequestBody List<TrainCoachDto> trainCoachDto){
        return new ResponseEntity<>(trainCoachService.createCoach(trainCoachDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        trainCoachService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<TrainCoachDto> update(@PathVariable Long id , @RequestBody TrainCoachDto trainCoachDto){
        return new ResponseEntity<>( trainCoachService.updateCoach(id, trainCoachDto),HttpStatus.OK);
    }

    @GetMapping
    public List<TrainCoachDto> getAll(){
        return  trainCoachService.getAllCoach();
    }

    @GetMapping("/{id}")
    public TrainCoachDto getById(@PathVariable Long id){
        return trainCoachService.getCoachById(id);
    }

    @GetMapping("/schedule/{scheduleid}")
    public List<TrainCoachDto> getByScheduleId(@PathVariable Long scheduleid){
        return trainCoachService.getCoachByScheduleId(scheduleid);
    }
}
