package practice.irctc.IRCTC.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings=new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles= new ArrayList<>();

}

