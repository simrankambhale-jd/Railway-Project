package practice.irctc.IRCTC.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import practice.irctc.IRCTC.DTO.Booking.BookingRequest;
import practice.irctc.IRCTC.DTO.Booking.BookingResponse;
import practice.irctc.IRCTC.DTO.Booking.TicketResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    List<TicketResponse> getAllBookings(Long userId);

    TicketResponse getBooking( String pnr);

    String cancelBooking(Long id);
}
