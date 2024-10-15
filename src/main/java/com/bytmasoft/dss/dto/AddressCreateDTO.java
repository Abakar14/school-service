package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressCreateDTO {

    private String city;
    private String country;
    private String street;
    private String streetNumber;
    private String zipCode;
}
