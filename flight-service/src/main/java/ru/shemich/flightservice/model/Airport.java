package ru.shemich.flightservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "city")
    String city;
    @Column(name = "country")
    String country;

    public String toCityAndName() {
        return city + " " + name;
    }

}


