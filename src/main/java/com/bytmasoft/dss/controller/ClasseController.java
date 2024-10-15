package com.bytmasoft.dss.controller;

import com.bytmasoft.common.controller.DSSCrud;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.ClasseCreateDTO;
import com.bytmasoft.dss.dto.ClasseResponseDTO;
import com.bytmasoft.dss.dto.ClasseUpdateDTO;
import com.bytmasoft.dss.entities.School;
import com.bytmasoft.dss.repositories.SchoolRepository;
import com.bytmasoft.dss.service.ClasseService;
import com.bytmasoft.dss.service.SchoolService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
import java.util.List;

@Schema(name ="Classe-Service")
@Validated
@AllArgsConstructor
@Tag(name = "Classe Service Api", description = "Classe Service for all Classe operations")
@RestController
@RequestMapping(value = "/classes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClasseController implements DSSCrud<ClasseResponseDTO, ClasseCreateDTO, ClasseUpdateDTO> {

    private final ClasseService classeService;


    @Override
    public ResponseEntity<ClasseResponseDTO> save(@Valid ClasseCreateDTO createDTO) {
        ClasseResponseDTO classeResponseDTO = classeService.add(createDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(classeResponseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(classeResponseDTO);
    }

    @Override
    public PagedModel<EntityModel<ClasseResponseDTO>> findAll(Pageable pageable) {
        return classeService.getAllClasses(pageable);
    }


    @Override
    public List<ClasseResponseDTO> findList() {
        return classeService.getAllClassesAsList();
    }

    @Override
    public ResponseEntity<ClasseResponseDTO> unlock(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(classeService.unlock(id));
    }

    @Override
    public ResponseEntity<ClasseResponseDTO> lockout(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(classeService.lockout(id));
    }

    @Override
    public ResponseEntity<ClasseResponseDTO> markfordeletion(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(classeService.markfordeletion(id));
    }

    @Override
    public Long countAll() {
        return classeService.countAll();
    }

    @Override
    public Long countAllActives() {
        return classeService.countAllActives();
    }

    @Override
    public Long countAllLocked() {
        return classeService.countAllLocked();
    }

    @Override
    public ResponseEntity<ClasseResponseDTO> findById(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(classeService.getClasseById(id));
    }

    @Override
    public ResponseEntity<ClasseResponseDTO> update(Long id, @Valid ClasseUpdateDTO classeUpdateDTO) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(classeService.updateClasse(id, classeUpdateDTO));
    }

    @Override
    public ResponseEntity<ClasseResponseDTO> delete(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(classeService.deleteClasseById(id));
    }

    @GetMapping("/name")
    ResponseEntity <ClasseResponseDTO> findClasseByName(@RequestParam String name){
        return ResponseEntity.ok(classeService.findClasseByName(name));
    }
}
