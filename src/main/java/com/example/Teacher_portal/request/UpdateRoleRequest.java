package com.example.Teacher_portal.request;

import com.example.Teacher_portal.model.Role.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateRoleRequest {

    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

}
