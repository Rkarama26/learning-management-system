package com.example.Teacher_portal.service;

import java.util.List;

import com.example.Teacher_portal.model.Role.Role;
import com.example.Teacher_portal.request.UpdateRoleRequest;

public interface RoleService {

	Role createRole(Role role);

	public Role updateRole(Long roleId, UpdateRoleRequest request);

	void deleteRole(Long roleId);

	List<Role> getAllRoles();

	Role getRoleById(Long roleId);
}
