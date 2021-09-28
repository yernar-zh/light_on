package com.example.light_on.repository;

import com.example.light_on.models.Country;
import com.example.light_on.models.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findByCountry(Country country);
}
