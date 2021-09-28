package com.example.light_on.controllers;

import com.example.light_on.models.Country;
import com.example.light_on.models.Room;
import com.example.light_on.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String main(Model model) {
        return "redirect:/rooms";
    }

    @GetMapping("/rooms")
    public String roomMain(Model model, @RequestAttribute("CURRENT_COUNTRY_ATTRIBUTE") Country curCountry) {
        List<Room> rooms = roomService.findByCountry(curCountry);
        model.addAttribute("rooms", rooms);
        return "roomMain";

    }

    @GetMapping("/rooms/add")
    public String roomAdd(Model model, @RequestAttribute("CURRENT_COUNTRY_ATTRIBUTE") Country curCountry) {
        List<Room> rooms = roomService.findByCountry(curCountry);
        model.addAttribute("rooms", rooms);
        return "roomAdd";
    }

    @PostMapping("/rooms/add")
    public String roomAddNew(
            @RequestParam String name, @RequestAttribute("CURRENT_COUNTRY_ATTRIBUTE") Country curCountry
    ) {
        Room room = new Room();
        room.setName(name);
        room.setCountry(curCountry);
        roomService.create(room);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isEmpty()) {
            return "redirect:/rooms";
        }
        Room room = roomOpt.get();
        model.addAttribute("room", room);
        return "roomDetails";
    }

    @PostMapping("/rooms/{id}/change")
    public String roomChangeLight(@PathVariable(value = "id") long id, Model model) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isEmpty()) {
            return "redirect:/rooms";
        }
        Room room = roomOpt.get();
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

}
