package com.example.Teacher_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Teacher_portal.model.Role.Role;
import com.example.Teacher_portal.request.UpdateRoleRequest;
import com.example.Teacher_portal.service.RoleService;

@RestController
@RequestMapping("/home/roles")
public class RoleController {
	

    @Autowired
    private RoleService roleService;
    

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @PutMapping("/update/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable Long roleId, @RequestBody UpdateRoleRequest request) {
        Role updatedRole = roleService.updateRole(roleId, request);
        if (updatedRole != null) {
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        Role role = roleService.getRoleById(roleId);
        return ResponseEntity.ok(role);
    }
    

	
}
