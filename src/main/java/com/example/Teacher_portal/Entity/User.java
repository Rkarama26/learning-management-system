package com.example.Teacher_portal.Entity;

import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role Role;

	private String lastName;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
	 * inverseJoinColumns = @JoinColumn(name = "role_id")) private List<Role> roles;
	 */
//  @JsonIgnore
//	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Appointments> studentAppointments;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Appointments> teacherAppointments;

	// @JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Availability> availability;

}