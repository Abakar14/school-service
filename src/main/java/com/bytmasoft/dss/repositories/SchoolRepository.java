package com.bytmasoft.dss.repositories;

import com.bytmasoft.dss.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
}
