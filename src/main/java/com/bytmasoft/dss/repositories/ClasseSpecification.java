package com.bytmasoft.dss.repositories;

import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.Classe;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

public class ClasseSpecification {

    public static Specification<Classe> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("name"), name);
        };
    }

    public static Specification<Classe> hasAddedBy(String addedBy) {
        return (root, query, cb) -> {
            if (addedBy == null || addedBy.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("addedBy"), addedBy);
        };
    }

    public static Specification<Address> hasModifiedBy(String modifiedBy) {
        return (root, query, cb) -> {
            if (modifiedBy == null || modifiedBy.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("modifiedBy"), modifiedBy);
        };
    }


    public static Specification<Classe> hasActive(boolean isActive) {
        return (root, query, cb) -> {
           return   cb.equal(root.get("isActive"), isActive);
        };
    }
}
