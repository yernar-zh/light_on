package com.example.light_on.repository;

import com.example.light_on.models.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country,Long> {

    Optional<Country> findByCode(String code);

}
