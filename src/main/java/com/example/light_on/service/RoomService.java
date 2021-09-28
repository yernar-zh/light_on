package com.example.light_on.service;

import com.example.light_on.models.Country;
import com.example.light_on.models.Room;
import com.example.light_on.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public void create(Room room) {
        room.setLightStatus(false);
        roomRepository.save(room);
    }

    public void change(Room room) {
        room.setLightStatus(!room.getLightStatus());
        roomRepository.save(room);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}
