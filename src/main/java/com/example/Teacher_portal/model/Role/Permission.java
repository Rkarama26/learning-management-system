package com.example.Teacher_portal.model.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	public Permission(String storedPermissionName) {
		// TODO Auto-generated constructor stub
	}
	


}
