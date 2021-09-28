package com.example.light_on.controllers;

import com.example.light_on.models.Country;
import com.example.light_on.models.Room;
import com.example.light_on.repo.CountryRepository;
import com.example.light_on.repo.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {

    private final RoomRepository roomRepository;
    private final CountryRepository countryRepository;

    public RoomController(RoomRepository roomRepository, CountryRepository countryRepository) {
        this.roomRepository = roomRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/")
    public String main(Model model) {

        return "redirect:/rooms";

    }

    @GetMapping("/rooms")
    public String roomMain(Model model) {

        Iterable<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "roomMain";

    }

    @GetMapping("/rooms/add")
    public String roomAdd(Model model) {

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);

        return "roomAdd";
    }

    @PostMapping("/rooms/add")
    public String roomAddNew(@RequestParam String name,
                             @RequestParam long cid,
                             Model model) {
        Room room = new Room();
        Optional<Country> country = countryRepository.findById(cid);
        room.setName(name);
        room.setCountry(country.get());
        roomRepository.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/{id}")
    public String blogDetails(@PathVariable(value="id") long id, Model model) {

        if (!roomRepository.existsById(id)) {
            return "redirect:/rooms";
        }

        Optional<Room> room = roomRepository.findById(id);
        ArrayList<Room> res = new ArrayList<>();
        room.ifPresent(res::add);
        model.addAttribute("room", res);
        return "roomDetails";
    }

    @PostMapping("/rooms/{id}/change")
    public String roomChangeLight(@PathVariable(value="id") long id, Model model) {

        Room room = roomRepository.findById(id).orElseThrow();
        room.setLightStatus(!room.getLightStatus());
        roomRepository.save(room);

        return "redirect:/rooms/{id}";
    }

    @PostMapping("/rooms/{id}/remove")
    public String removeRoom(@PathVariable(value="id") long id, Model model) {

        Room room = roomRepository.findById(id).orElseThrow();
        roomRepository.delete(room);

        return "redirect:/rooms";
    }

}
