package com.bytmasoft.dss.entities;

import com.bytmasoft.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity implements Serializable {

    private String city;
    private String country;
     private String street;
    private String streetNumber;
    private String zipCode;
    @Builder.Default
    @Column(columnDefinition = "Boolean default false")
    private boolean deleted = false;
}
