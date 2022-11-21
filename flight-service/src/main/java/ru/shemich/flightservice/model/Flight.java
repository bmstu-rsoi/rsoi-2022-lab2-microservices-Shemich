package ru.shemich.flightservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "flight_number", length = 20)
    String flightNumber;
    @Column(name = "datetime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    Date datetime;
    @Column(name = "from_airport_id")
    Long fromAirportId;
    @Column(name = "to_airport_id")
    Long toAirportId;
    @Column(name = "price")
    Long price;
}

