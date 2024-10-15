package com.bytmasoft.dss.repositories;

import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.Employee;
import com.bytmasoft.dss.entities.School;
import com.bytmasoft.dss.enums.Gender;
import org.springframework.data.jpa.domain.Specification;

public class SchoolSpecification {

    public static Specification<School> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("name"), name);
        };
    }


    public static Specification<School> hasAddedBy(String addedBy) {
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

    public static Specification<School> hasActive(boolean isActive) {
        return (root, query, cb) -> {
            return   cb.equal(root.get("isActive"), isActive);
        };
    }


}
