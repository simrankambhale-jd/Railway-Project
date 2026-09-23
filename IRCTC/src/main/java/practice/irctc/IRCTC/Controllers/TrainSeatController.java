
package practice.irctc.IRCTC.Controllers;

import practice.irctc.IRCTC.DTO.TrainSeatDto;
import practice.irctc.IRCTC.Services.TrainSeatService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train/seat")
public class TrainSeatController {

    private final TrainSeatService trainSeatService;

    public TrainSeatController(TrainSeatService trainSeatService) {
        this.trainSeatService = trainSeatService;
    }

    @PostMapping
    public ResponseEntity<List<TrainSeatDto>> createSeats(@RequestBody List<TrainSeatDto> dto) {
        return new ResponseEntity<>(trainSeatService.createSeats(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainSeatDto> getSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(trainSeatService.getSeatById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainSeatDto>> getAllSeats() {
        return ResponseEntity.ok(trainSeatService.getAllSeats());
    }

    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<TrainSeatDto>> getSeatsByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(trainSeatService.getSeatsByCoach(coachId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainSeatDto> updateSeat(@PathVariable Long id, @RequestBody TrainSeatDto dto) {
        return ResponseEntity.ok(trainSeatService.updateSeat(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long id) {
        trainSeatService.deleteSeat(id);
        return ResponseEntity.ok("Seat deleted successfully");
    }
}
