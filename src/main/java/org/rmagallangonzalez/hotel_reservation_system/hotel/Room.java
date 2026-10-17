package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Getter 
@Setter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "rooms", uniqueConstraints = @UniqueConstraint(columnNames = {"hotel_id", "number"}))
public class Room {
    @Id 
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ToString.Include
    @Column(nullable = false)
    private String number;

    @ToString.Include
    @Column(nullable = false)
    private int capacity;

    @ToString.Include
    @Column(nullable = false)
    private BigDecimal pricePerNight;

    public Room(Hotel hotel, String number, int capacity, BigDecimal pricePerNight) {
        this.hotel = hotel;
        this.number = number;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }
}
