package com.bytmasoft.dss.repositories;

import com.bytmasoft.dss.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClasseRepository extends JpaRepository<Classe, Long>, JpaSpecificationExecutor<Classe> {

    Classe findByName(String name);

}
