package com.bytmasoft.dss.mapper;


import com.bytmasoft.dss.dto.AddressCreateDTO;
import com.bytmasoft.dss.dto.AddressResponseDTO;
import com.bytmasoft.dss.dto.AddressUpdateDTO;
import com.bytmasoft.dss.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    AddressResponseDTO toAddressResponseDTO(Address address);
    Address toAddress(AddressCreateDTO addressCreateDTO);
    void updateAddress(AddressUpdateDTO addressUpdateDTO, @MappingTarget Address address);

}
