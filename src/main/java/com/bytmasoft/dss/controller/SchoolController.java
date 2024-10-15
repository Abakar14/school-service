package com.bytmasoft.dss.controller;

import com.bytmasoft.common.controller.DSSCrud;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.*;
import com.bytmasoft.dss.service.SchoolService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@Schema(name ="School-Service")
@Validated
@AllArgsConstructor
@Tag(name = "School Service Api", description = "School Service for all School operations")
@RestController
@RequestMapping(value = "/schools", produces = MediaType.APPLICATION_JSON_VALUE)
public class SchoolController implements DSSCrud<SchoolResponseDTO, SchoolCreateDTO, SchoolUpdateDTO> {

    private final SchoolService schoolService;

    @Override
    public ResponseEntity<SchoolResponseDTO> save(@Valid SchoolCreateDTO schoolCreateDTO) {
        SchoolResponseDTO schoolResponseDTO = schoolService.add(schoolCreateDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(schoolResponseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(schoolResponseDTO);
    }

    @Override
    public PagedModel<EntityModel<SchoolResponseDTO>> findAll(Pageable pageable) {
        return schoolService.findAllSchools(pageable);
    }


    @Override
    public List<SchoolResponseDTO> findList() {
        return schoolService.getAllSchoolsAsList();
    }

    @Override
    public ResponseEntity<SchoolResponseDTO> unlock(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(schoolService.unlock(id));
    }

    @Override
    public ResponseEntity<SchoolResponseDTO> lockout(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(schoolService.lockout(id));
    }

    @Override
    public ResponseEntity<SchoolResponseDTO> markfordeletion(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(schoolService.markfordeletion(id));
    }

    @Override
    public Long countAll() {
        return schoolService.countAll();
    }

    @Override
    public Long countAllActives() {
        return schoolService.countAllActives();
    }

    @Override
    public Long countAllLocked() {
        return schoolService.countAllLocked();
    }

    @Override
    public ResponseEntity<SchoolResponseDTO> findById(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(schoolService.getSchoolById(id));
    }

    @Override
    public ResponseEntity<SchoolResponseDTO> update(Long id, @Valid SchoolUpdateDTO dto) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(schoolService.updateSchool(id, dto));
    }

    @Override
    public ResponseEntity<SchoolResponseDTO> delete(Long id) throws DSSEntityNotFoundException {
        return ResponseEntity.ok(schoolService.deleteSchoolById(id));
    }

    @PutMapping("/{school_id}/teachers/{teacher_id}")
    ResponseEntity<SchoolResponseDTO> addTeacher(@PathVariable Long school_id ,  @PathVariable Long teacher_id){
        return ResponseEntity.ok(schoolService.addTeacher(school_id, teacher_id));
    }
    @PutMapping("/{school_id}/teachers")
    ResponseEntity<SchoolResponseDTO> addTeachers(@PathVariable Long school_id ,  @RequestParam(required = false) List<Long> teacherIds){
        return ResponseEntity.ok(schoolService.addTeachers(school_id, teacherIds));
    }

    @PutMapping("/{school_id}/students/{student_id}")
    ResponseEntity<SchoolResponseDTO> addStudent(@PathVariable Long school_id ,  @PathVariable Long student_id){
        return ResponseEntity.ok(schoolService.addStudent(school_id, student_id));
    }
    @PutMapping("/{school_id}/students")
    ResponseEntity<SchoolResponseDTO> addStudents(@PathVariable Long school_id ,  @RequestParam(required = false) List<Long> studentIds){
        return ResponseEntity.ok(schoolService.addStudents(school_id, studentIds));
    }

    @PutMapping("/{school_id}/employees/{employee_id}")
    ResponseEntity<SchoolResponseDTO> addEmployee(@PathVariable Long school_id ,  @PathVariable Long employee_id){
        return ResponseEntity.ok(schoolService.addEmployee(school_id, employee_id));
    }
    @PutMapping("/{school_id}/employees")
    ResponseEntity<SchoolResponseDTO>  addEmployees(@PathVariable Long school_id ,  @RequestParam(required = false) List<Long> employeeIds){
        return ResponseEntity.ok(schoolService.addEmployees(school_id, employeeIds));
    }

    @PutMapping("/{school_id}/classes/{classe_id}")
    ResponseEntity<SchoolResponseDTO>  addClasse(@PathVariable Long school_id ,  @PathVariable Long classe_id){
        return ResponseEntity.ok(schoolService.addClasse(school_id, classe_id));
    }
    @PutMapping("/{school_id}/classes")
    ResponseEntity<SchoolResponseDTO>  addEmployee(@PathVariable Long school_id ,  @RequestParam(required = false) List<Long> classeIds){
        return ResponseEntity.ok(schoolService.addClasses(school_id, classeIds));
    }

    @PutMapping("/{school_id}/courses/{course_id}")
    ResponseEntity<SchoolResponseDTO>  addCourse(@PathVariable Long school_id ,  @PathVariable Long course_id){
        return ResponseEntity.ok(schoolService.addCourse(school_id, course_id));
    }
    @PutMapping("/{school_id}/courses")
     ResponseEntity<SchoolResponseDTO> addCourses(@PathVariable Long school_id ,  @RequestParam(required = false) List<Long> courseIds){
        return ResponseEntity.ok(schoolService.addCourses(school_id, courseIds));
    }

    @GetMapping("/filter")
    PagedModel<EntityModel<SchoolResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @ParameterObject
            @Parameter(description = "Pagination information", required = false, schema = @Schema(implementation = Pageable.class))
            @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){

        return schoolService.findAllByFilter(name, pageable);
    }

    @PutMapping("/{school_id}/students/remove")
    ResponseEntity<SchoolResponseDTO> removeStudent(@PathVariable Long school_id ,  @RequestParam List<Long> studentIds){
        return ResponseEntity.ok(schoolService.removeStudent(school_id, studentIds));
    }

    @PutMapping("/{school_id}/teachers/remove")
    ResponseEntity<SchoolResponseDTO> removeTeacher(@PathVariable Long school_id ,  @RequestParam List<Long> teacherIds){
        return ResponseEntity.ok(schoolService.removeTeacher(school_id, teacherIds));
    }

    @PutMapping("/{school_id}/courses/remove")
    ResponseEntity<SchoolResponseDTO> removeCourse(@PathVariable Long school_id ,  @RequestParam List<Long> courseIds){
        return ResponseEntity.ok(schoolService.removeCourse(school_id, courseIds));
    }

}
