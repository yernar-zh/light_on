package com.example.light_on.repository;

import com.example.light_on.models.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
