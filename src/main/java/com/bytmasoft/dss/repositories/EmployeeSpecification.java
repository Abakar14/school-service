package com.bytmasoft.dss.repositories;

import com.bytmasoft.dss.entities.Address;
import com.bytmasoft.dss.entities.Classe;
import com.bytmasoft.dss.entities.Employee;
import com.bytmasoft.dss.enums.Gender;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> hasFirstName(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null || firstName.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("firstName"), firstName);
        };
    }


    public static Specification<Employee> hasLastName(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null || lastName.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("lastName"), lastName);
        };
    }

    public static Specification<Employee> hasPlaceOfBirth(String placeOfBirth) {
        return (root, query, cb) -> {
            if (placeOfBirth == null || placeOfBirth.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("placeOfBirth"), placeOfBirth);
        };
    }

    public static Specification<Employee> hasPlaceOfBirth(Gender gender) {
        return (root, query, cb) -> {
            if (gender == null || gender.toString().isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("gender"), gender);
        };
    }

    public static Specification<Employee> hasAddedBy(String addedBy) {
        return (root, query, cb) -> {
            if (addedBy == null || addedBy.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("addedBy"), addedBy);
        };
    }

    public static Specification<Employee> hasModifiedBy(String modifiedBy) {
        return (root, query, cb) -> {
            if (modifiedBy == null || modifiedBy.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("modifiedBy"), modifiedBy);
        };
    }

    public static Specification<Employee> hasActive(boolean isActive) {
        return (root, query, cb) -> {
            return   cb.equal(root.get("isActive"), isActive);
        };
    }


}
