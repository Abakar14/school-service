package com.bytmasoft.dss.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity_local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean isActive = true;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime addedOn;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedOn;

    private String addedBy;
    private String modifiedBy;

}
