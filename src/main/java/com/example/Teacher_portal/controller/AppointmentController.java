package com.example.Teacher_portal.controller;

import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.ResourceNotFoundException;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.service.AppointmentService;
import com.example.Teacher_portal.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @PostMapping("/book/{availabilityId}")
    public ResponseEntity<Appointments> bookAppointmentHandler(@RequestHeader("Authorization") String jwt,
                                                               @PathVariable Long availabilityId) throws BadRequestException, UserException, ResourceNotFoundException {
        return ResponseEntity.ok(appointmentService.createAppointment(availabilityId, jwt));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PatchMapping("/{appointmentId}/confirme")
    public ResponseEntity<Appointments> confirmAppointment(@RequestHeader("Authorization") String jwt,
                                                           @PathVariable Long appointmentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(appointmentService.confirmAppointment(appointmentId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PatchMapping("/{appointmentId}/decline")
    public ResponseEntity<Appointments> declineAppointment(@RequestHeader("Authorization") String jwt,
                                                           @PathVariable Long appointmentId) throws BadRequestException {
        return ResponseEntity.ok(appointmentService.declineAppointment(appointmentId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{appointmentId}/reschedule")
    public ResponseEntity<Appointments> rescheduleAppointment(@RequestHeader("Authorization") String jwt,
                                                              @PathVariable Long appointmentId, Long newAvailabilitySlotId) throws ResourceNotFoundException {


        return ResponseEntity.ok(appointmentService.rescheduleAppointment(appointmentId, newAvailabilitySlotId));

    }
    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/book/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentHandler(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long appointmentId) throws BadRequestException {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointments>> getUserAppointments(@RequestHeader("Authorization") String jwt,
                                                                  @PathVariable Long userId) {
        List<Appointments> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/all")
    public ResponseEntity<List<Appointments>> getAllAppointments(@RequestHeader("Authorization") String jwt) {

        List<Appointments> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<Appointments>> getAppointmentsByUser(@RequestHeader("Authorization") String jwt)
            throws UserException {

        User user = userService.findUserprofileByJwt(jwt);

        List<Appointments> appointments = appointmentService.getAppointmentsByUserId(user.getId());
        return ResponseEntity.ok(appointments);
    }

}
