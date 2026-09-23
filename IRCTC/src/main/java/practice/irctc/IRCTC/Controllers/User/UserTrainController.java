package practice.irctc.IRCTC.Controllers.User;


import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.AvailableTrainResponse;
import practice.irctc.IRCTC.DTO.TrainCoachDto;
import practice.irctc.IRCTC.DTO.TrainSeatDto;
import practice.irctc.IRCTC.DTO.UserTrainSearchRequest;
import practice.irctc.IRCTC.Services.TrainSeatService;
import practice.irctc.IRCTC.Services.TrainService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserTrainController {
    private TrainService trainService;
    private TrainSeatService seatService;

    public UserTrainController(TrainService trainService,TrainSeatService seatService){
        this.trainService=trainService;
        this.seatService=seatService;
    }
    @PostMapping("/train")
    public List<AvailableTrainResponse> searchTrain(@RequestBody UserTrainSearchRequest userTrainSearchRequest){
       return trainService.searchTrain(userTrainSearchRequest);
    }

    @GetMapping("/seats/{trainScheduleId}/{coachId}")
    public List<TrainSeatDto> searchSeatsByCoach(@PathVariable Long trainScheduleId, @PathVariable Long coachId){
        return seatService.searchSeatsByCoach(trainScheduleId,coachId);
    }

}
