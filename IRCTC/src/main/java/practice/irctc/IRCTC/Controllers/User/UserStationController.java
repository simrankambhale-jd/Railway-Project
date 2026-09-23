package practice.irctc.IRCTC.Controllers.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.irctc.IRCTC.DTO.StationsDto;
import practice.irctc.IRCTC.Services.StationService;

import java.util.List;

@RestController
@RequestMapping("/user/stations")
public class UserStationController {
    private final StationService stationService;

    public UserStationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public List<StationsDto> getAllStations() {
        return stationService.getAllStations();
    }
}
