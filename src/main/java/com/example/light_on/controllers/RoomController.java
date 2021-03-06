package com.example.light_on.controllers;

import com.example.light_on.exception.ForbiddenException;
import com.example.light_on.models.Country;
import com.example.light_on.models.Room;
import com.example.light_on.service.CountryService;
import com.example.light_on.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {

    private final CountryService countryService;
    private final RoomService roomService;

    public RoomController(CountryService countryService, RoomService roomService) {
        this.countryService = countryService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String main(Model model) {
        return "redirect:/rooms";
    }

    @GetMapping("/rooms")
    public String roomMain(Model model) {
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        return "roomMain";

    }

    @GetMapping("/rooms/add")
    public String roomAdd(Model model) {
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        return "roomAdd";
    }

    @PostMapping("/rooms/add")
    public String roomAddNew(
            @RequestParam String name,
            @RequestParam Long countryId
    ) {
        Country country = countryService.findById(countryId).orElseThrow();
        Room room = new Room();
        room.setName(name);
        room.setCountry(country);
        roomService.create(room);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/{id}")
    public String blogDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isEmpty()) {
            return "redirect:/rooms";
        }
        Room room = roomOpt.get();
        model.addAttribute("room", room);
        return "roomDetails";
    }

    @PostMapping("/rooms/{id}/change")
    public String roomChangeLight(
            @PathVariable(value = "id") Long id,
            @RequestAttribute("CURRENT_COUNTRY_ATTRIBUTE") Country curCountry
    ) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isEmpty()) {
            return "redirect:/rooms";
        }
        Room room = roomOpt.get();
        if (!room.getCountry().equals(curCountry)) {
            throw new ForbiddenException();
        }
        roomService.change(room);
        return "redirect:/rooms/" + id;
    }

    @PostMapping("/rooms/{id}/remove")
    public String removeRoom(@PathVariable(value = "id") long id, Model model) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isEmpty()) {
            return "redirect:/rooms";
        }
        Room room = roomOpt.get();
        roomService.delete(room);
        return "redirect:/rooms";
    }


    @ExceptionHandler(ForbiddenException.class)
    public String databaseError() {
        return "forbidden";
    }

}
