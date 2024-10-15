package com.bytmasoft.dss.mapper;

import com.bytmasoft.dss.dto.ClasseCreateDTO;
import com.bytmasoft.dss.dto.ClasseResponseDTO;
import com.bytmasoft.dss.dto.ClasseUpdateDTO;
import com.bytmasoft.dss.entities.Classe;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClasseMapper {


    @Mapping( target = "schoolId", source = "school.id")
    ClasseResponseDTO classeToClasseResponseDTO(Classe classe);
    Classe classeCreateDTOToClasse(ClasseCreateDTO createDTO);
    void updateClasse(ClasseUpdateDTO classeUpdateDTO, @MappingTarget Classe classe);
}
