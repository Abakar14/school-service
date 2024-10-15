package com.bytmasoft.dss.entities;

import com.bytmasoft.common.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class Classe extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 100)
    private String name;
    private String description;
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

}
