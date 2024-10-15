package com.bytmasoft.dss.dto;


import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateDTO {


    private String firstName;
    private String lastName;
    private Date birthday;
    private String placeOfBirth;
    private Gender gender;
    private Long addressId;
    private Long schoolId;
}
