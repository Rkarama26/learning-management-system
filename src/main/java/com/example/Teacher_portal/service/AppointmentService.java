package com.example.Teacher_portal.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Teacher_portal.model.Appointments;
import com.example.Teacher_portal.model.Availability;
import com.example.Teacher_portal.model.Status;
import com.example.Teacher_portal.model.User;
import com.example.Teacher_portal.repository.AppointmentRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.AppointmentRequest;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	public Appointments createAppointment(AppointmentRequest request) throws BadRequestException {
		
		User student = userRepository.findById(request.getStudentId()).orElseThrow();
        User teacher = userRepository.findById(request.getTeacherId()).orElseThrow();
        
        // Retrieve teacher availability
        boolean isTeacherAvailable = isTeacherAvailable(teacher, request.getDayOfWeek() , request.getStartTime(), request.getEndTime());
        if (!isTeacherAvailable) {
            throw new BadRequestException("No teacher available during the specified time range.");
        }
        
        // Create new appointment
        Appointments appointment = new Appointments();
        appointment.setStudent(student);
        appointment.setTeacher(teacher);
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());
        appointment.setStatus(Status.PENDING); 
        
        
        return appointmentRepository.save(appointment);
	}

	private boolean isTeacherAvailable(User teacher, DayOfWeek dayOfWeek, LocalTime localTime, LocalTime localTime2) {
		
	    List<Availability> availabilities = teacher.getAvailability();
	    
	    //the teacher has any availability at all
	    if (availabilities == null || availabilities.isEmpty()) {
	        return false;
	    }
	    
	    //specified day of week
	    Availability availability = availabilities.stream()
	            .filter(a -> a.getDayOfWeek() == dayOfWeek)
	            .findFirst()
	            .orElse(null);
	    
	    if (availability == null) {
	        return false;
	    }
	    
	    //  specified time range
	    LocalTime availabilityStartTime = availability.getStartTime();
	    LocalTime availabilityEndTime = availability.getEndTime();
	    
	    if (!(localTime.isAfter(availabilityStartTime) && localTime2.isBefore(availabilityEndTime))) {
	    
	        return false;
	    }
	    
	    return true;
	}

}
