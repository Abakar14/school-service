package com.bytmasoft.dss.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDTO {

    private Long id;
    private Boolean isActive;
    private LocalDateTime addedOn;
    private LocalDateTime modifiedOn;
    private String addedBy;
    private String modifiedBy;


    private String city;
    private String country;
    private String street;
    private String streetNumber;
    private String zipCode;

}
