package com.example.light_on.repo;

import com.example.light_on.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country,Long> {
}
