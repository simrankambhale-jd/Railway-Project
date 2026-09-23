package practice.irctc.IRCTC.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.StationsDto;
import practice.irctc.IRCTC.DTO.TrainScheduleDto;
import practice.irctc.IRCTC.Services.StationService;
import practice.irctc.IRCTC.Services.TrainScheduleService;

import java.util.List;

@RestController
@RequestMapping("/admin/train/schedule")
public class TrainScheduleController {

    private TrainScheduleService trainScheduleService;

    public TrainScheduleController( TrainScheduleService trainScheduleService){
        this.trainScheduleService=trainScheduleService;
    }

    @PostMapping
    public ResponseEntity<TrainScheduleDto> create(@RequestBody TrainScheduleDto trainScheduleDto){
        return new ResponseEntity<>(trainScheduleService.createSchedule(trainScheduleDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        trainScheduleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<TrainScheduleDto> update(@PathVariable Long id ,@RequestBody TrainScheduleDto trainScheduleDto){
        return new ResponseEntity<>( trainScheduleService.updateSchedule(id,trainScheduleDto),HttpStatus.OK);
    }

    @GetMapping
    public List<TrainScheduleDto> getAll(){
        return  trainScheduleService.getAllSchedule();
    }

    @GetMapping("/{id}")
    public TrainScheduleDto getById(@PathVariable Long id){
        return  trainScheduleService.getScheduleById(id);
    }

    @GetMapping("/train/{trainid}")
    public List<TrainScheduleDto> getByTrainId(@PathVariable Long trainid){
        return  trainScheduleService.getScheduleByTrainId(trainid);
    }
}
