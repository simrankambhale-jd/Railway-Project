package practice.irctc.IRCTC.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practice.irctc.IRCTC.DTO.Booking.*;
import practice.irctc.IRCTC.DTO.TrainSeatDto;
import practice.irctc.IRCTC.Entity.*;
import practice.irctc.IRCTC.Exceptions.ResourceNotFoundException;
import practice.irctc.IRCTC.Repository.*;
import practice.irctc.IRCTC.Services.BookingService;
import practice.irctc.IRCTC.Services.TrainSeatService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepo bookingRepo;
    private PassengerRepo passengerRepo;
    private UserRepo userRepo;
    private TrainScheduleRepo trainScheduleRepo;
    private TrainRepo trainRepo;
    private StationsRepo stationRepo;
    private ModelMapper modelMapper;
    private TrainSeatRepo trainSeatRepo;
    private TrainCoachRepo trainCoachRepo;
    private TrainSeatServiceImpl seatService;

    public BookingServiceImpl(BookingRepo bookingRepo, PassengerRepo passengerRepo, UserRepo userRepo, TrainScheduleRepo trainScheduleRepo, TrainRepo trainRepo, StationsRepo stationRepo, ModelMapper modelMapper, TrainSeatRepo trainSeatRepo, TrainCoachRepo trainCoachRepo) {
        this.bookingRepo=bookingRepo;
        this.passengerRepo=passengerRepo;
        this.stationRepo=stationRepo;
        this.userRepo = userRepo;
        this.trainScheduleRepo=trainScheduleRepo;
        this.trainRepo=trainRepo;
        this.trainSeatRepo=trainSeatRepo;
        this.modelMapper = modelMapper;
        this.trainCoachRepo=trainCoachRepo;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request){

        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        TrainSchedule trainSchedule = this.trainScheduleRepo.findById(request.getTrainScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Train Schedule not found with id: " + request.getTrainScheduleId()));

        Stations sourceStation = stationRepo.findById(request.getSourceStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Source Station not found with id: " + request.getSourceStationId()));

        Stations destinationStation = stationRepo.findById(request.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination Station not found with id: " + request.getDestinationStationId()));

        TrainCoach coach = trainCoachRepo.findById(request.getCoachId())
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found"));


        String pnr=UUID.randomUUID().toString();
             Booking book =new Booking();
             book.setPnr(pnr);
             book.setUser(user);
             book.setTrainSchedule(trainSchedule);
             book.setTrain(trainSchedule.getTrainId());
             book.setSourceStation(sourceStation);
             book.setDestinationStation(destinationStation);
             book.setJourneyDate(trainSchedule.getRundate());
             book.setStatus(BookingStatus.CONFIRMED);
             book.setCoach(coach);
             book.setBookedAt(LocalDateTime.now());

        List<Passenger> passengerList=new ArrayList<>();
        Double totalPrice=0.0;
        // this is return after book object because we want to set book object here
        for(PassengerDto passenger : request.getPassengers()){

            TrainSeat seat=trainSeatRepo.findById(passenger.getSeatId()).orElseThrow(()-> new ResourceNotFoundException("No seats available in this type of coach"));
            if(!seat.getAvailable()){
                throw new ResourceNotFoundException("Seat is booked");
            }
            Passenger passenger1 = new Passenger();

            passenger1.setAge(passenger.getAge());
            passenger1.setName(passenger.getName());
            passenger1.setGender(passenger.getGender());
            passenger1.setSeat(seat);
            passenger1.setBerthPreference(passenger.getBerthPreference());
            passenger1.setBooking(book);

            passengerList.add(passenger1);

            totalPrice+=seat.getPrice();

            seat.setAvailable(false);
            trainSeatRepo.save(seat);
        }
        book.setPassengers(passengerList);
        book.setTotalFare(totalPrice);
        bookingRepo.save(book);

          passengerRepo.saveAll(passengerList);

          BookingResponse response=new BookingResponse();
          response.setPNR(pnr);
          response.setUserId(user.getId());
          response.setTrainId(trainSchedule.getTrainId().getId());
          response.setTrainScheduleId(trainSchedule.getId());
          response.setSourceStationId(sourceStation.getId());
          response.setDestinationStationId(destinationStation.getId());
          response.setTotalFare(totalPrice);
          response.setCoachId(request.getCoachId());
          List<PassengerDto> passengerDtos= passengerList.stream().map(pass->modelMapper.map(pass,PassengerDto.class)).toList();
          response.setPassengers(passengerDtos);
          response.setStatus(BookingStatus.CONFIRMED);
          response.setJourneyDate(trainSchedule.getRundate());


          return response;
    }

    @Override
   public  List<TicketResponse> getAllBookings(Long userId){
      List<Booking> bookings=bookingRepo.findByUser(userId);
      List<TicketResponse> responseList=new ArrayList<>();

      for(Booking book:bookings) {
          TicketResponse response = new TicketResponse();
          response.setId(book.getId());
          response.setUserName(book.getUser().getName());
          response.setPNR(book.getPnr());
          response.setStatus(book.getStatus());
          response.setJourneyDate(book.getJourneyDate());
          response.setTotalFare(book.getTotalFare());
          response.setCoach(book.getCoach().getCoachNumber());
          response.setCoachType(book.getCoach().getCoach());
          response.setSourceStationName(book.getSourceStation().getName());
          response.setDestinationStationName(book.getDestinationStation().getName());
          response.setTrainCode(book.getTrain().getTrainNo());
          response.setTrainName(book.getTrain().getName());

          List<TicketPassenger> passengersList=new ArrayList<>();

          for(Passenger passenger: book.getPassengers()) {
              TicketPassenger passenger1 = new TicketPassenger();
              passenger1.setAge(passenger.getAge());
              passenger1.setName(passenger.getName());
              passenger1.setGender(passenger.getGender());
              passenger1.setSeatNumber(passenger.getSeat().getSeatNumber());
              passenger1.setBerthType(passenger.getBerthPreference());

              passengersList.add(passenger1);
          }

          response.setPassengers(passengersList);
          responseList.add(response);
      }
      return responseList;
    }

    @Override
    public TicketResponse getBooking( String pnr){
       Booking book=bookingRepo.findByPnr(pnr);

        TicketResponse response = new TicketResponse();
        response.setId(book.getId());

        response.setUserName(book.getUser().getName());
        response.setPNR(book.getPnr());
        response.setStatus(book.getStatus());
        response.setJourneyDate(book.getJourneyDate());
        response.setTotalFare(book.getTotalFare());
        response.setCoach(book.getCoach().getCoachNumber());
        response.setCoachType(book.getCoach().getCoach());
        response.setSourceStationName(book.getSourceStation().getName());
        response.setDestinationStationName(book.getDestinationStation().getName());
        response.setTrainCode(book.getTrain().getTrainNo());
        response.setTrainName(book.getTrain().getName());

        List<TicketPassenger> passengersList=new ArrayList<>();

        for(Passenger passenger: book.getPassengers()) {
            TicketPassenger passenger1 = new TicketPassenger();
            passenger1.setAge(passenger.getAge());
            passenger1.setName(passenger.getName());
            passenger1.setGender(passenger.getGender());
            passenger1.setSeatNumber(passenger.getSeat().getSeatNumber());
            passenger1.setBerthType(passenger.getBerthPreference());

            passengersList.add(passenger1);
        }

        response.setPassengers(passengersList);
        return response;
    }

    @Override
    public String cancelBooking(Long id){
      Booking booking=  bookingRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking id not found"));
      booking.setStatus(BookingStatus.CANCELLED);
      for(Passenger passenger: booking.getPassengers()){
         TrainSeat seat= trainSeatRepo.findById(passenger.getSeat().getId()).orElseThrow(()->new ResourceNotFoundException("Seat id not found"));
         seat.setAvailable(true);
          trainSeatRepo.save(seat);
      }
      bookingRepo.save(booking);
        String mssg="Booking canceled";
        return  mssg;
    }
}
