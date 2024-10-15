package com.bytmasoft.dss.controller;

import com.bytmasoft.common.controller.DSSCrud;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.AddressCreateDTO;
import com.bytmasoft.dss.dto.AddressResponseDTO;
import com.bytmasoft.dss.dto.AddressUpdateDTO;
import com.bytmasoft.dss.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Schema(name ="Address-Service")
@Validated
@AllArgsConstructor
@Tag(name = "Address Service Api", description = "Address Service for all Address operations")
@RestController
@RequestMapping(value = "/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController implements DSSCrud<AddressResponseDTO, AddressCreateDTO, AddressUpdateDTO> {

    private final AddressService addressService;

    @Operation(summary = "Save Address", description = "Add a address to address table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "address added successfully ")
    })
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Override
    public ResponseEntity<AddressResponseDTO> save(@Valid AddressCreateDTO addressCreateDTO) {
        AddressResponseDTO addressResponseDTO = addressService.add(addressCreateDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(addressResponseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(addressResponseDTO);
    }

    @Override
    public PagedModel<EntityModel<AddressResponseDTO>> findAll(Pageable pageable) {
        return addressService.getAllAddresses(pageable);
    }


    @GetMapping("/filter")
    PagedModel<EntityModel<AddressResponseDTO>> findAll(
            @RequestParam(required = false) String country,
            @RequestParam (required = false) String city,
            @RequestParam (required = false) String street,
            @RequestParam (required = false) String zipCode,

            @ParameterObject
            @Parameter(description = "Pagination information", required = false, schema = @Schema(implementation = Pageable.class))
            @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){

        return addressService.findAllByFilter(country,city,  street, zipCode, pageable);
    }

    @Override
    public List<AddressResponseDTO> findList() {
        return addressService.getAllAddressesAsList();
    }

    @Override
    public ResponseEntity<AddressResponseDTO> unlock(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(addressService.unlock(id));
    }

    @Override
    public ResponseEntity<AddressResponseDTO> lockout(Long id) throws DSSEntityNotFoundException {
       return ResponseEntity.ok(addressService.lockout(id));
    }

    @Override
    public ResponseEntity<AddressResponseDTO> markfordeletion(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(addressService.markfordeletion(id));
    }

    @Override
    public Long countAll() {
        return addressService.countAll();
    }

    @Override
    public Long countAllActives() {
        return addressService.countAllActives();
    }

    @Override
    public Long countAllLocked() {
        return addressService.countAllLocked();
    }

    @Override
    public ResponseEntity<AddressResponseDTO> findById(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @Override
    public ResponseEntity<AddressResponseDTO> update(Long id, @Valid AddressUpdateDTO addressUpdateDTO) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(addressService.updateAddress(id, addressUpdateDTO));
    }

    @Override
    public ResponseEntity<AddressResponseDTO> delete(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(addressService.deleteAddressById(id));
    }
}
