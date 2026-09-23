package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String gender;

    @Enumerated(EnumType.STRING)
    private BerthType berthPreference;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private TrainSeat seat;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
