package com.example.Teacher_portal.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Teacher_portal.exception.RoleAlreadyExistsException;
import com.example.Teacher_portal.model.Role.Permission;
import com.example.Teacher_portal.model.Role.Role;
import com.example.Teacher_portal.repository.PermissionRepository;
import com.example.Teacher_portal.repository.RoleRepository;
import com.example.Teacher_portal.request.UpdateRoleRequest;
import com.example.Teacher_portal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Role createRole(Role role) {
		// if role exist
		Role existingRole = roleRepository.findByName(role.getName());
		if (existingRole != null) {
			throw new RoleAlreadyExistsException("Role with name " + role.getName() + " already exists");
		}
		String upperCaseRoleName = role.getName().toUpperCase();
		role.setName(upperCaseRoleName);

		// parent role if provided
		if (role.getParentRole() != null) {
			Long parentRoleId = role.getParentRole().getId();
			Role parentRole = roleRepository.findById(parentRoleId).orElseThrow(
					() -> new IllegalArgumentException("Parent role with ID " + parentRoleId + " not found"));
			role.setParentRole(parentRole); // valid parent role
		}

		return roleRepository.save(role);
	}

	@Override
	 public Role updateRole(Long roleId, UpdateRoleRequest request) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            role.setName(request.getName());
            role.setDescription(request.getDescription());
            role.setPermissions(request.getPermissions());
            return roleRepository.save(role);
        } else {
            return null;
        }
    }

	@Override
	public void deleteRole(Long roleId) {
		Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " not found"));
		roleRepository.delete(role);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId)
				.orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " not found"));
	}

}
