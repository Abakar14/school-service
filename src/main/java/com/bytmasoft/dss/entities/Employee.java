package com.bytmasoft.dss.entities;

import com.bytmasoft.common.entities.BaseEntity;
import com.bytmasoft.dss.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity implements Serializable {


    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String lastName;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private String placeOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

}
