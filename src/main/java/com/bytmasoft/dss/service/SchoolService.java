package com.bytmasoft.dss.service;

import com.bytmasoft.common.exception.DSSBadRequestExpception;
import com.bytmasoft.common.exception.DSSEntityNotFoundException;
import com.bytmasoft.dss.dto.SchoolCreateDTO;
import com.bytmasoft.dss.dto.SchoolResponseDTO;
import com.bytmasoft.dss.dto.SchoolUpdateDTO;
import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.Classe;
import com.bytmasoft.dss.entities.Employee;
import com.bytmasoft.dss.entities.School;
import com.bytmasoft.dss.mapper.SchoolMapper;
import com.bytmasoft.dss.repositories.*;
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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;
    private final AppUtils appUtils;
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final ClasseRepository classeRepository;
    private final PagedResourcesAssembler<SchoolResponseDTO> pagedResourcesAssembler;

    public SchoolResponseDTO add(@Valid SchoolCreateDTO schoolCreateDTO) {

        Address address = null;
        if(schoolCreateDTO.getAddressId() == null) {
            throw new DSSBadRequestExpception("Add school without address is not allowed");
        }
        if(schoolCreateDTO.getAddressId() != null) {
            address = addressRepository.findById(schoolCreateDTO.getAddressId()).orElseThrow(() -> new DSSEntityNotFoundException("Address with id: "+schoolCreateDTO.getAddressId()+" not found"));
        }
        School school = schoolMapper.toSchool(schoolCreateDTO);
        school.setAddress(address);
        school.setAddedBy(appUtils.getUsername());
        School savedClasse = schoolRepository.save(school);
        return schoolMapper.toSchoolResponseDTO(savedClasse);
    }

    public PagedModel<EntityModel<SchoolResponseDTO>> getAllSchools(Pageable pageable) {
        Page<School> schools = schoolRepository.findAll(pageable);
        Page<SchoolResponseDTO> dtoPage =  schools.map(schoolMapper::toSchoolResponseDTO);
        return pagedResourcesAssembler.toModel(dtoPage);
    }

    public List<SchoolResponseDTO> getAllSchoolsAsList() {
        return schoolRepository.findAll().stream().map(schoolMapper::toSchoolResponseDTO).collect(Collectors.toUnmodifiableList());

    }

    public SchoolResponseDTO unlock(Long id) {
        School school = findSchoolById(id);
        school.setIsActive(true);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO( schoolRepository.save(school));
    }

    public School findSchoolById(Long id) throws DSSEntityNotFoundException{
        return schoolRepository.findById(id).orElseThrow(() -> new DSSEntityNotFoundException("School with id: "+id+" not found"));

    }

    public SchoolResponseDTO lockout(Long id) {
        School school = findSchoolById(id);
        school.setIsActive(false);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO markfordeletion(Long id) {
        School school = findSchoolById(id);
        school.setDeleted(true);
        school.setModifiedBy(appUtils.getUsername());
       return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public Long countAll() {
        return schoolRepository.count();
    }

    public Long countAllActives() {
        return schoolRepository.count(SchoolSpecification.hasActive(true));
    }

    public Long countAllLocked() {
        return schoolRepository.count(SchoolSpecification.hasActive(false));
    }

    public SchoolResponseDTO getSchoolById(Long id) {
       return schoolMapper.toSchoolResponseDTO(findSchoolById(id));
    }

    public SchoolResponseDTO updateSchool(Long id, @Valid SchoolUpdateDTO dto) {
        School school = findSchoolById(id);
        school.setModifiedBy(appUtils.getUsername());
        schoolMapper.updateSchool(dto, school);
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO deleteSchoolById(Long id) {
        School school = findSchoolById(id);
        schoolRepository.delete(school);
        return schoolMapper.toSchoolResponseDTO(school);
    }

    public PagedModel<EntityModel<SchoolResponseDTO>> findAllByFilter(String name, Pageable pageable) {

        Specification<School> spec = Specification.where(SchoolSpecification.hasName(name));

        Page<School> page = schoolRepository.findAll(spec, pageable);
        Page<SchoolResponseDTO> pageDto = page.map(schoolMapper::toSchoolResponseDTO);
        return pagedResourcesAssembler.toModel(pageDto);
    }


    //TODO check if the student exisit??
    public SchoolResponseDTO addStudent(Long schoolId, Long studentId) {
        School school = findSchoolById(schoolId);
        school.getStudentIds().add(studentId);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    //TODO check if the students exisit??
    public SchoolResponseDTO addStudents(Long schoolId, List<Long> studentIds) {
        School school = findSchoolById(schoolId);
        school.getStudentIds().addAll(studentIds);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }


    public SchoolResponseDTO addEmployee(Long schoolId, Long employeeId) {
        School school = findSchoolById(schoolId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new DSSEntityNotFoundException("Employee with id: "+employeeId+" not found"));
        school.getEmployees().add(employee);
        school.setModifiedBy(appUtils.getUsername());
        schoolRepository.save(school);
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO addEmployees(Long schoolId, List<Long> employeeIds) {
        School school = findSchoolById(schoolId);
        employeeRepository.findAllById(employeeIds).forEach(employee -> school.getEmployees().add(employee));
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO addClasse(Long schoolId, Long classeId) {
        School school = findSchoolById(schoolId);
        Classe classe = classeRepository.findById(classeId).orElseThrow(() -> new DSSEntityNotFoundException("Classe with id: "+classeId+" not found"));
        school.getClasses().add(classe);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO addClasses(Long schoolId, List<Long> classeIds) {
        School school = findSchoolById(schoolId);
        classeRepository.findAllById(classeIds).forEach(classe -> school.getClasses().add(classe));
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    //TODO check if the teacher exisit??
    public SchoolResponseDTO addTeacher(Long schoolId, Long teacherId) {
        School school = findSchoolById(schoolId);
        school.getTeacherIds().add(teacherId);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }
    //TODO check if the teacher exisit??
    public SchoolResponseDTO addTeachers(Long schoolId, List<Long> teacherIds) {
        School school = findSchoolById(schoolId);
        school.getTeacherIds().addAll(teacherIds);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    //TODO check if the course exisit??
    public SchoolResponseDTO addCourse(Long schoolId, Long courseId) {
        School school = findSchoolById(schoolId);
        school.getCourseIds().add(courseId);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    //TODO check if the course exisit??
    public SchoolResponseDTO addCourses(Long schoolId, List<Long> courseIds) {
        School school = findSchoolById(schoolId);
        school.getCourseIds().addAll(courseIds);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }


    public PagedModel<EntityModel<SchoolResponseDTO>> findAllSchools(Pageable pageable) {
        Page<School> page = schoolRepository.findAll(pageable);
        Page<SchoolResponseDTO> dtoPage = page.map(schoolMapper::toSchoolResponseDTO);
        return pagedResourcesAssembler.toModel(dtoPage);
    }

    public SchoolResponseDTO removeCourse(Long schoolId, List<Long> courseIds) {
        School school = findSchoolById(schoolId);
        school.getCourseIds().removeAll(courseIds);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO removeTeacher(Long schoolId, List<Long> teacherIds) {
        School school = findSchoolById(schoolId);
        school.getTeacherIds().removeAll(teacherIds);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }

    public SchoolResponseDTO removeStudent(Long schoolId, List<Long> studentIds) {
        School school = findSchoolById(schoolId);
        school.getStudentIds().removeAll(studentIds);
        school.setModifiedBy(appUtils.getUsername());
        return schoolMapper.toSchoolResponseDTO(schoolRepository.save(school));
    }
}
