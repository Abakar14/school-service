package com.bytmasoft.dss.entities;

import com.bytmasoft.common.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schools")
public class School extends BaseEntity implements Serializable {

    @Column(nullable = false)
    @Size(min = 3, max = 200)
    private String name;

    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String description;

    @Column(nullable = false)
    private String phone;

    private String email;
    private String website;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Classe> classes;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;

    @ElementCollection
    @CollectionTable(name = "school_courses", joinColumns = @JoinColumn(name = "school_id"))
    @Column(name = "course_id")
    private List<Long> courseIds;

    @ElementCollection
    @CollectionTable(name = "school_students", joinColumns = @JoinColumn(name = "school_id"))
    @Column(name = "student_id")
    private List<Long> studentIds;

    @ElementCollection
    @CollectionTable(name = "school_teachers", joinColumns = @JoinColumn(name = "school_id"))
    @Column(name = "teacher_id")
    private List<Long> teacherIds;

    @Builder.Default
    @Column(columnDefinition = "Boolean default false")
    private boolean deleted = false;
}
