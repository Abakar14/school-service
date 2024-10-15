package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateDTO {

    private String firstName;
    private String lastName;
     private Long addressId;
}
