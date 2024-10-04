package com.example.Teacher_portal.Entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
public class Availability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private DayOfWeek dayOfWeek;

	@Column(unique = true)
	private LocalDateTime startTime;
	
	@Column(unique = true)
	private LocalDateTime endTime;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "appointment_id")
	private Appointments appointment;

}