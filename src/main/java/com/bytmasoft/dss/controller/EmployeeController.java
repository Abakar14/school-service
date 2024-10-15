package com.bytmasoft.dss.controller;

import com.bytmasoft.common.controller.DSSCrud;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.AddressResponseDTO;
import com.bytmasoft.dss.dto.EmployeeCreateDTO;
import com.bytmasoft.dss.dto.EmployeeResponseDTO;
import com.bytmasoft.dss.dto.EmployeeUpdateDTO;
import com.bytmasoft.dss.enums.Gender;
import com.bytmasoft.dss.service.EmployeeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;


@Schema(name ="Employee-Service")
@Validated
@AllArgsConstructor
@Tag(name = "Employee Service Api", description = "Employee Service for all Employee operations")
@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController implements DSSCrud<EmployeeResponseDTO, EmployeeCreateDTO, EmployeeUpdateDTO> {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponseDTO> save(@Valid EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.add(employeeCreateDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(employeeResponseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(employeeResponseDTO);
    }

    @Override
    public PagedModel<EntityModel<EmployeeResponseDTO>> findAll(Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }


    @Override
    public List<EmployeeResponseDTO> findList() {
        return employeeService.getAllEmployeesAsList();
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> unlock(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(employeeService.unlock(id));
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> lockout(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(employeeService.lockout(id));
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> markfordeletion(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(employeeService.markfordeletion(id));
    }

    @Override
    public Long countAll() {
        return employeeService.countAll();
    }

    @Override
    public Long countAllActives() {
        return employeeService.countAllActives();
    }

    @Override
    public Long countAllLocked() {
        return employeeService.countAllLocked();
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> findById(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> update(Long id, @Valid EmployeeUpdateDTO employeeUpdateDTO) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeUpdateDTO));
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> delete(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(employeeService.deleteClasseById(id));
    }

    @GetMapping("/filter")
    PagedModel<EntityModel<EmployeeResponseDTO>> findAll(
            @RequestParam (required = false) String firstName,
            @RequestParam (required = false) String lasatName,
            @RequestParam (required = false) Integer age,
            @RequestParam (required = false) Date birthday,
            @RequestParam (required = false) Gender gender,

            @ParameterObject
            @Parameter(description = "Pagination information", required = false, schema = @Schema(implementation = Pageable.class))
            @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){

        return employeeService.findAllByFilter(firstName,lasatName, age, birthday, gender, pageable);
    }
}

