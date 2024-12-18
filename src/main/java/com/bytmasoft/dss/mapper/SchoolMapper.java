package com.bytmasoft.dss.mapper;

import com.bytmasoft.dss.dto.AddressCreateDTO;
import com.bytmasoft.dss.dto.SchoolCreateDTO;
import com.bytmasoft.dss.dto.SchoolResponseDTO;
import com.bytmasoft.dss.dto.SchoolUpdateDTO;
import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.School;
import org.mapstruct.*;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Mapper(componentModel = "spring", uses = {ClasseMapper.class, EmployeeMapper.class, AddressMapper.class})
public interface SchoolMapper {

    // Mapping from CreateDTO to School entity
    @Mapping(target = "address", source = "addressCreateDTO", qualifiedByName = "mapCreatAddressToAddress") // ID is not set during creation
    //@Mapping(target = "address.id", source = "addressId") // Map the addressId to address.id
    School toSchool(SchoolCreateDTO schoolCreateDTO);


    // Mapping from School entity to ResponseDTO
    //@Mapping(source = "address", target = "address")
    //@Mapping(source = "classes", target = "classes")
    //@Mapping(source = "employees", target = "employees")
    SchoolResponseDTO toSchoolResponseDTO(School school);


    void updateSchool(SchoolUpdateDTO dto,  @MappingTarget School school);

 @Named("mapCreatAddressToAddress")
default Address mapCreatAddressToAddress(AddressCreateDTO addressCreateDTO){
     Address address = new Address();
     address.setCity(addressCreateDTO.getCity());
     address.setCountry(addressCreateDTO.getCountry());
     address.setStreet(addressCreateDTO.getStreet());
     address.setPostalCode(addressCreateDTO.getPostalCode());
     address.setStreetNumber(addressCreateDTO.getStreetNumber());

     return address;

 }

}
