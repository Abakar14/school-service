package com.bytmasoft.dss.service;

import com.bytmasoft.common.exception.DSSBadRequestExpception;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.*;
import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.Classe;
import com.bytmasoft.dss.entities.Employee;
import com.bytmasoft.dss.entities.School;
import com.bytmasoft.dss.enums.Gender;
import com.bytmasoft.dss.mapper.EmployeeMapper;
import com.bytmasoft.dss.repositories.AddressSpecification;
import com.bytmasoft.dss.repositories.ClasseSpecification;
import com.bytmasoft.dss.repositories.EmployeeRepository;
import com.bytmasoft.dss.repositories.EmployeeSpecification;
import com.bytmasoft.dss.utils.AppUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AppUtils appUtils;
    private final SchoolService schoolService;
    private final AddressService addressService;
    private final PagedResourcesAssembler<EmployeeResponseDTO> pagedResourcesAssembler;

     public EmployeeResponseDTO add(@Valid EmployeeCreateDTO employeeCreateDTO) {

         School school = null;

         if(employeeCreateDTO.getSchoolId() == null) {
             throw new DSSBadRequestExpception("Add Employee without school is not allowed");
         }

         if(employeeCreateDTO.getSchoolId() != null) {
             school = schoolService.findSchoolById(employeeCreateDTO.getSchoolId());
         }

         if(employeeCreateDTO.getAddressId() != null) {
             addressService.findAddressById(employeeCreateDTO.getAddressId());
         }
         Employee employee = employeeMapper.toEmployee(employeeCreateDTO);
         employee.setSchool(school);
         employee.setAddedBy(appUtils.getUsername());
         Employee savedEmployee = employeeRepository.save(employee);
         return employeeMapper.toEmployeeResponseDTO(savedEmployee);
    }

    public PagedModel<EntityModel<EmployeeResponseDTO>> getAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        Page<EmployeeResponseDTO> dtoPage =  employees.map(employeeMapper::toEmployeeResponseDTO);
        return pagedResourcesAssembler.toModel(dtoPage);
    }

    public List<EmployeeResponseDTO> getAllEmployeesAsList() {
        return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponseDTO).collect(Collectors.toUnmodifiableList());

    }

    public EmployeeResponseDTO unlock(Long id) {
        Employee employee = findEmployeeById(id);
        employee.setIsActive(true);
        employee.setModifiedBy(appUtils.getUsername());
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new DSSEntityNotFoundException("Employee with id: "+id+" not found"));

    }

    public EmployeeResponseDTO lockout(Long id) {
        Employee employee = findEmployeeById(id);
        employee.setIsActive(false);
        employee.setModifiedBy(appUtils.getUsername());
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    public EmployeeResponseDTO markfordeletion(Long id) {
        Employee employee = findEmployeeById(id);
        employee.setModifiedBy(appUtils.getUsername());
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    public Long countAll() {
        return employeeRepository.count();
    }

    public Long countAllActives() {

            return employeeRepository.count(EmployeeSpecification.hasActive(true));
    }
    public Long countAllLocked() {
        return employeeRepository.count(EmployeeSpecification.hasActive(false));
    }


    public EmployeeResponseDTO getEmployeeById(Long id) {
        return employeeMapper.toEmployeeResponseDTO(findEmployeeById(id));
    }

    public EmployeeResponseDTO updateEmployee(Long id, @Valid EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = findEmployeeById(id);
        employee.setModifiedBy(appUtils.getUsername());
        employeeMapper.updateEmployee(employeeUpdateDTO, employee);
        return employeeMapper.toEmployeeResponseDTO(employeeRepository.save(employee));
    }

    public EmployeeResponseDTO deleteClasseById(Long id) {
        Employee employee = findEmployeeById(id);
        employeeRepository.delete(employee);
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    public PagedModel<EntityModel<EmployeeResponseDTO>> findAllByFilter(String firstName, String lasatName, Integer age, Date birthday, Gender gender, Pageable pageable) {
        Page<Employee> classes = employeeRepository.findAll(pageable);
        Page<EmployeeResponseDTO> dtoPage =  classes.map(employeeMapper::toEmployeeResponseDTO);
        return pagedResourcesAssembler.toModel(dtoPage);
     }


}
