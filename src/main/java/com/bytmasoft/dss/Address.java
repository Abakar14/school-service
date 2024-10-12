package com.bytmasoft.dss;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String city;
    private String country;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String zipCode;


}
