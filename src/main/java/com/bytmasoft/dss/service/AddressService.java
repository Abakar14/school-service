package com.bytmasoft.dss.service;

import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.AddressCreateDTO;
import com.bytmasoft.dss.dto.AddressResponseDTO;
import com.bytmasoft.dss.dto.AddressUpdateDTO;
import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.enums.Gender;
import com.bytmasoft.dss.mapper.AddressMapper;
import com.bytmasoft.dss.repositories.AddressRepository;
import com.bytmasoft.dss.repositories.AddressSpecification;
import com.bytmasoft.dss.utils.AppUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AppUtils appUtils;
    private final PagedResourcesAssembler<AddressResponseDTO> pagedResourcesAssembler;

    public AddressResponseDTO add(@Valid AddressCreateDTO addressCreateDTO) {
        Address address = addressMapper.toAddress(addressCreateDTO);
        address.setAddedBy(appUtils.getUsername());
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toAddressResponseDTO(savedAddress);
    }

    public AddressResponseDTO getAddressById(Long id) {
        return addressMapper.toAddressResponseDTO(findAddressById(id));
    }

    public List<AddressResponseDTO> getAllAddressesAsList() {
        return addressRepository.findAll().stream().map(addressMapper::toAddressResponseDTO).collect(Collectors.toUnmodifiableList());
    }

    public AddressResponseDTO updateAddress(Long id, @Valid AddressUpdateDTO addressUpdateDTO) {
        Address addressToUpdate = findAddressById(id);
        addressToUpdate.setModifiedBy(appUtils.getUsername());
        addressMapper.updateAddress(addressUpdateDTO, addressToUpdate);
        return addressMapper.toAddressResponseDTO(addressRepository.save(addressToUpdate));
    }

    public AddressResponseDTO deleteAddressById(Long id) {
        Address addressToDelete = findAddressById(id);
        addressRepository.delete(addressToDelete);
        return addressMapper.toAddressResponseDTO(addressToDelete);
    }

    public Address findAddressById(Long id) throws DSSEntityNotFoundException {
        return addressRepository.findById(id).orElseThrow(() -> new DSSEntityNotFoundException("Address with id: "+id+" not found"));
    }

    public PagedModel<EntityModel<AddressResponseDTO>> getAllAddresses(Pageable pageable) {
        Page<Address> addresses = addressRepository.findAll(pageable);
        Page<AddressResponseDTO> dtoPage =  addresses.map(addressMapper::toAddressResponseDTO);
        return pagedResourcesAssembler.toModel(dtoPage);
    }

    public AddressResponseDTO unlock(Long id) {
        Address address = findAddressById(id);
        address.setIsActive(true);
        address.setModifiedBy(appUtils.getUsername());
        addressRepository.save(address);
        return addressMapper.toAddressResponseDTO(address);
    }

    public AddressResponseDTO lockout(Long id) {
        Address address = findAddressById(id);
        address.setIsActive(false);
        address.setModifiedBy(appUtils.getUsername());
        addressRepository.save(address);
        return addressMapper.toAddressResponseDTO(address);
    }

    public AddressResponseDTO markfordeletion(Long id) {
        Address address = findAddressById(id);
        address.setModifiedBy(appUtils.getUsername());
        address.setIsActive(false);
        address.setDeleted(true);
        addressRepository.save(address);
        return addressMapper.toAddressResponseDTO(address);
    }

    public Long countAll() {
        return addressRepository.count();
    }

    public Long countAllActives() {
        return addressRepository.count(AddressSpecification.hasActive(true));
    }

    public Long countAllLocked() {
        return addressRepository.count(AddressSpecification.hasActive(false));
    }

    public PagedModel<EntityModel<AddressResponseDTO>> findAllByFilter(String  country, String city, String street, String zipCode, Pageable pageable) {
        Specification<Address> spec = Specification.where(AddressSpecification.hasCountry(country))
                .and(AddressSpecification.hasCity(city))
                .and(AddressSpecification.hasStreet(street))
                .and(AddressSpecification.hasZipCode(zipCode));
        Page<Address> page = addressRepository.findAll(spec, pageable);
        Page<AddressResponseDTO> pageDto = page.map(addressMapper::toAddressResponseDTO);
        return pagedResourcesAssembler.toModel(pageDto);

    }
}
