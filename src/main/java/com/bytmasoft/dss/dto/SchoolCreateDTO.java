package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolCreateDTO {

    private String name;
    private String description;
    private String email;
    private String phone;
    private String website;
    private AddressCreateDTO addressCreateDTO;
    private List<Long> courseIds;
    private List<Long> employeeIds;
    private List<Long> classeIds;

}
