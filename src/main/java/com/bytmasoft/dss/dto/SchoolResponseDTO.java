package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String website;
    private boolean deleted;
    private Boolean isActive;
    private LocalDateTime addedOn;
    private LocalDateTime modifiedOn;
    private String addedBy;
    private String modifiedBy;
    private AddressResponseDTO address;
    private List<ClasseResponseDTO> classes;
    private List<EmployeeResponseDTO> employees;
    private List<Long> courseIds;
    private List<Long> studentIds;
    private List<Long> teacherIds;
}
