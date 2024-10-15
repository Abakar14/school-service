package com.bytmasoft.dss.service;

import com.bytmasoft.common.exception.DSSBadRequestExpception;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.AddressResponseDTO;
import com.bytmasoft.dss.dto.ClasseCreateDTO;
import com.bytmasoft.dss.dto.ClasseResponseDTO;
import com.bytmasoft.dss.dto.ClasseUpdateDTO;
import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.Classe;
import com.bytmasoft.dss.entities.School;
import com.bytmasoft.dss.mapper.ClasseMapper;
import com.bytmasoft.dss.repositories.AddressSpecification;
import com.bytmasoft.dss.repositories.ClasseRepository;
import com.bytmasoft.dss.repositories.ClasseSpecification;
import com.bytmasoft.dss.utils.AppUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ClasseService {

    private final ClasseRepository classeRepository;
    private final ClasseMapper classeMapper;
    private final AppUtils appUtils;
    private final PagedResourcesAssembler<ClasseResponseDTO> pagedResourcesAssembler;
    private final SchoolService schoolService;

    public ClasseResponseDTO add(@Valid ClasseCreateDTO createDTO) {
        School school = null;
        if(createDTO.getSchoolId() == null) {
            throw new DSSBadRequestExpception("Add classe without school is not allowed");
        }
        if(createDTO.getSchoolId() != null) {
            school = schoolService.findSchoolById(createDTO.getSchoolId());
        }

        Classe classe = classeMapper.classeCreateDTOToClasse(createDTO);
        classe.setSchool(school);
        classe.setAddedBy(appUtils.getUsername());
        Classe savedClasse = classeRepository.save(classe);
        return classeMapper.classeToClasseResponseDTO(savedClasse);
    }

    public PagedModel<EntityModel<ClasseResponseDTO>> getAllClasses(Pageable pageable) {
        Page<Classe> classes = classeRepository.findAll(pageable);
        Page<ClasseResponseDTO> dtoPage =  classes.map(classeMapper::classeToClasseResponseDTO);
        return pagedResourcesAssembler.toModel(dtoPage);
    }

    public List<ClasseResponseDTO> getAllClassesAsList() {
        return classeRepository.findAll().stream().map(classeMapper::classeToClasseResponseDTO).collect(Collectors.toUnmodifiableList());

    }

    public ClasseResponseDTO unlock(Long id) {
        Classe classes = findClasseById(id);
        classes.setIsActive(true);
        classes.setModifiedBy(appUtils.getUsername());
        classeRepository.save(classes);
        return classeMapper.classeToClasseResponseDTO(classes);
    }

    public ClasseResponseDTO lockout(Long id) {
        Classe classe = findClasseById(id);
        classe.setIsActive(false);
        classe.setModifiedBy(appUtils.getUsername());
        classeRepository.save(classe);
        return classeMapper.classeToClasseResponseDTO(classe);
    }

    public ClasseResponseDTO markfordeletion(Long id) {
        Classe classe = findClasseById(id);
        classe.setModifiedBy(appUtils.getUsername());
        classeRepository.save(classe);
        return classeMapper.classeToClasseResponseDTO(classe);
    }

    public Long countAll() {
        return classeRepository.count();
    }

    public Long countAllActives() {
        return classeRepository.count(ClasseSpecification.hasActive(true));
    }

    public Long countAllLocked() {
        return classeRepository.count(ClasseSpecification.hasActive(false));
    }

    public ClasseResponseDTO getClasseById(Long id) {
        return classeMapper.classeToClasseResponseDTO(findClasseById(id));
    }

    private Classe findClasseById(Long id) {
        return classeRepository.findById(id).orElseThrow(() -> new DSSEntityNotFoundException("Classe with id: "+id+" not found"));

    }

    public ClasseResponseDTO updateClasse(Long id, @Valid ClasseUpdateDTO classeUpdateDTO) {
        Classe classeToUpdate = findClasseById(id);
        classeToUpdate.setModifiedBy(appUtils.getUsername());
        classeMapper.updateClasse(classeUpdateDTO, classeToUpdate);
        return classeMapper.classeToClasseResponseDTO(classeRepository.save(classeToUpdate));
    }

    public ClasseResponseDTO deleteClasseById(Long id) {
        Classe classeToDelete = findClasseById(id);
        classeRepository.delete(classeToDelete);
        return classeMapper.classeToClasseResponseDTO(classeToDelete);
    }

    public ClasseResponseDTO findClasseByName(String name) {
        return classeMapper.classeToClasseResponseDTO(findByName(name));
    }

    private Classe findByName(String name) {
        Classe classe = classeRepository.findByName(name);
        if (classe == null) {
            throw new DSSEntityNotFoundException("Classe with name: "+name+" not found");
        }
        return classe;
    }
}
