package com.example.Teacher_portal.controller;

import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.request.ReqAvailability;
import com.example.Teacher_portal.service.AvailabilityService;
import com.example.Teacher_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/user")
    public ResponseEntity<List<Availability>> getUserAvailability(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserprofileByJwt(jwt);
        Long userId = user.getId();

        List<Availability> availabilities = availabilityService.getUserAvailability(userId);
        return ResponseEntity.ok(availabilities);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<Availability> createUserAvailability(@RequestHeader("Authorization") String jwt,
                                                               @RequestBody Availability available) throws Exception {

        Availability availability = availabilityService.createUserAvailability(jwt, available);
        return ResponseEntity.ok(availability);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Availability> updateAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody ReqAvailability req) {
        Availability updatedAvailability = availabilityService.updateAvailability(id, req);
        return ResponseEntity.ok(updatedAvailability);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Availability>> getAllAvailableSlots(@RequestHeader("Authorization") String jwt) {

        List<Availability> available = availabilityService.getAllAvailableSlots();
        return ResponseEntity.ok(available);
    }


}
