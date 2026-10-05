package org.rmagallangonzalez.hotel_reservation_system.hotel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Getter 
@Setter 
@ToString 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hotels")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hotel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    private String name;
    private String address;

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
