package com.example.Teacher_portal.request;

import java.util.Set;

import com.example.Teacher_portal.model.Role.Permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {
	
	private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

}
