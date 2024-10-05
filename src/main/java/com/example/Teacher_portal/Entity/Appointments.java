package com.example.Teacher_portal.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Appointments {

	public Appointments(String string) {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private User student;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	private User teacher;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private AppointmentStatus status;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "availability_id", referencedColumnName = "id") // Foreign key
    private Availability availability;
	
	@CreationTimestamp
	private LocalDateTime bookingTime;

}
