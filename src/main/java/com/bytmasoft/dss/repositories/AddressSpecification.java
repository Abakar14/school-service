package com.bytmasoft.dss.repositories;

import com.bytmasoft.dss.entities.Address;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<Address> hasCountry(String country) {
        return (root, query, cb) -> {
            if (country == null || country.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("country"), country);
        };
    }

    public static Specification<Address> hasCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("city"), city);
        };
    }

    public static Specification<Address> hasStreet(String street) {
        return (root, query, cb) -> {
            if (street == null || street.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("street"), street);
        };
    }

    public static Specification<Address> hasZipCode(String zipCode) {
        return (root, query, cb) -> {
            if (zipCode == null || zipCode.isEmpty()) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("zipCode"), zipCode);
        };
    }
    public static Specification<Address> hasActive(Boolean isActive) {
        return (root, query, cb) -> {
            if (isActive == null) {
                return cb.conjunction();
            }
            return   cb.equal(root.get("isActive"), isActive);
        };
    }
}
