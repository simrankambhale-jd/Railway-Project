package practice.irctc.IRCTC.Controllers.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.irctc.IRCTC.DTO.Booking.BookingRequest;
import practice.irctc.IRCTC.DTO.Booking.BookingResponse;
import practice.irctc.IRCTC.DTO.Booking.TicketResponse;
import practice.irctc.IRCTC.Services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/user/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request){
      return new ResponseEntity<>(bookingService.createBooking(request), HttpStatus.CREATED);
 }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TicketResponse>> getAllBookings(@PathVariable Long userId){
        return new ResponseEntity<>(bookingService.getAllBookings(userId),HttpStatus.OK);
    }

    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<TicketResponse> getBooking(@PathVariable String pnr){
        return new ResponseEntity<>(bookingService.getBooking(pnr),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.cancelBooking(id),HttpStatus.OK);
    }
}

