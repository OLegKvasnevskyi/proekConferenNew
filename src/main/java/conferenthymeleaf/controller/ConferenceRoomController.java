package conferenthymeleaf.controller;

import conferenthymeleaf.entity.ConferenceRoom;
import conferenthymeleaf.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController

public class ConferenceRoomController {
    static final int DEFAULT_PAGE_SIZE = 2;
    private final ConferenceRoomService conferenceRoomService;

    @Autowired
    ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = conferenceRoomService;
    }

    @GetMapping("/conferencerooms/")
    public String index() {
        return "conferencerooms/index";
    }

    @GetMapping("conferencerooms/list")
    public String getConferenceRooms(final Model model,
                                     @RequestParam(value = "page", defaultValue = " 0") final int pageNumber,
                                     @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE + "") final int pageSize) {
        final Page<ConferenceRoom> page = conferenceRoomService.getConferenceRooms(pageNumber, pageSize);

        final int currentPageNumber = page.getNumber();
        final int previousPageNumber = page.hasPrevious() ? currentPageNumber - 1 : -1;
        final int nextPageNumber = page.hasNext() ? currentPageNumber + 1 : -1;

        model.addAttribute("conferencerooms", page.getContent());
        model.addAttribute("previousPageNumber", previousPageNumber);
        model.addAttribute("nextPageNumber", nextPageNumber);
        model.addAttribute("currentPageNumber", currentPageNumber);
        model.addAttribute("pageSize", pageSize);
        return "conferencerooms/list";
    }

    @GetMapping("/conferencerooms/add")
    public String add(final Model model) {
        model.addAttribute("conferenceroom", new ConferenceRoom());
        return "conferencerooms/add";
    }

    @GetMapping("/conferencerooms/view")
    public String view(final Model model, @RequestParam final Integer id) {
        final Optional<ConferenceRoom> record = conferenceRoomService.getConferenceRoom(id);
        model.addAttribute("conferenceroom", record.isPresent() ? record.get() : new ConferenceRoom());
        model.addAttribute("id", id);
        return "conferencerooms/view";
    }

    @GetMapping("/conferencerooms/edit")
    public String edit(final Model model, @RequestParam final Integer id) {
        final Optional<ConferenceRoom> record = conferenceRoomService.getConferenceRoom(id);
        model.addAttribute("conferenceroom", record.isPresent() ? record.get() : new ConferenceRoom());
        model.addAttribute("id", id);
        return "conferencerooms/edit";
    }

    @PostMapping("/conferencerooms/save")
    public String save(final Model model,
                       @ModelAttribute final ConferenceRoom conferenceRoom,
                       final BindingResult errors) {
        conferenceRoomService.save(conferenceRoom);
        return "redirect:list";
    }

    @GetMapping("/conferencerooms/delete")
    public String delete(final Model model, @RequestParam final Integer id) {
        final Optional<ConferenceRoom> record = conferenceRoomService.getConferenceRoom(id);
        model.addAttribute("conferenceroom", record.isPresent() ? record.get() : new ConferenceRoom());
        model.addAttribute("id", id);
        return "conferencerooms/delete";
    }

    @PostMapping("/conferencerooms/delete")
    public String deletion(final Model model, @RequestParam final Integer id) {
        conferenceRoomService.delete(id);
        return "redirect:list";
    }
    @GetMapping("/conferencerooms/myerror")
    public String error() {
        return "/conferencerooms/myerror";
    }


/* DDDDDDDDDD
    //--------------------------------------------------------------
    /*  /reservations/findasc

    @GetMapping("/findasc")
    List<ConferenceRoomEntity> getConferenceRoomsAll(@RequestParam(required = false) String name) {
        return conferenceRoomService.getConferenceRoomsAll(name);
    }


    //--------------------------------------------------------------
    @GetMapping("/{name}")
    ResponseEntity<ConferenceRoomEntity> getConferenceRoomByName(@PathVariable String name) {
        ConferenceRoomEntity result = conferenceRoomService.getConferenceRoomByName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //--------------------------------------------------------------
    @DeleteMapping("/{name}")
    ResponseEntity<String> removeConferenceRoom(@PathVariable("name") String name) {
        conferenceRoomService.removeConferenceRoomByName(name);
        return new ResponseEntity<>("Removed the organization: " + name, HttpStatus.OK);
    }

    //--------------------------------------------------------------
    @PostMapping
    ResponseEntity<String> addConferenceRoom(@Valid @RequestBody ConferenceRoomEntity conferenceRoomEntity) {
        conferenceRoomService.addConferenceRoom(conferenceRoomEntity);
        return new ResponseEntity<>("OK" + conferenceRoomEntity.getName(), HttpStatus.OK);
    }

    //--------------------------------------------------------------

    @PatchMapping
    ResponseEntity<String> updateConferenceRoom(@RequestParam("old_name_org") String
                                                        oldNameOrg, @RequestParam("new_name_org") String newNameOrg) {
        conferenceRoomService.updateConferenceRoomn(oldNameOrg, newNameOrg);
        return new ResponseEntity<>(String.format("Updated organization: %s to %s", oldNameOrg, newNameOrg), HttpStatus.OK);
    }

    /* DDDDDDDDDD
    //--------------------------------------------------------------
    //--------------------------------------------------------------

    //po dostępności

/*
    @GetMapping("/{availability}")
    List<ConferenceRoomEntity> getConferenceRoomsAll(@RequestParam(required = false) Boolean availability) {
        return conferenceRoomService.getConferenceRoomByAvailability(availability);
    }




//    * liczbie miejsc


    @GetMapping("/numerofseats/{numerofseats}")
    List<ConferenceRoomEntity> getConferenceRoomsAll(@RequestParam(required = false) Integer numberOfSeats) {
        return conferenceRoomService.getConferenceRoomByNumberOfSeats(numberOfSeats);
    }


 */


}
