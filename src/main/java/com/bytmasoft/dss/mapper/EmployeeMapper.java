package com.bytmasoft.dss.mapper;

import com.bytmasoft.dss.dto.EmployeeCreateDTO;
import com.bytmasoft.dss.dto.EmployeeResponseDTO;
import com.bytmasoft.dss.dto.EmployeeUpdateDTO;
import com.bytmasoft.dss.entities.Employee;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping( target = "schoolId", source = "school.id")
    EmployeeResponseDTO toEmployeeResponseDTO(Employee employee);

    Employee toEmployee(EmployeeCreateDTO employeeCreateDTO);

    void updateEmployee(EmployeeUpdateDTO dto, @MappingTarget Employee employee);
}
