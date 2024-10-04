package com.example.Teacher_portal.jwt;

import java.util.Arrays;
import java.util.List;

public class PermissionMiddleWare {
	
	
	    private static final List<String> ADMIN_PERMISSIONS = Arrays.asList("CREATE_USER", "UPDATE_USER", "DELETE_USER", "VIEW_USER");
	    private static final List<String> MODERATOR_PERMISSIONS = Arrays.asList("UPDATE_USER", "VIEW_USER");
	    private static final List<String> USER_PERMISSIONS = Arrays.asList("VIEW_USER");

	    
	    public static boolean hasPermission(String role, String permission) {
	        List<String> permissions = getPermissions(role);
	        return permissions.contains(permission);
	    }
	    
	    private static List<String> getPermissions(String role) {
	        switch (role.toUpperCase()) {
	            case "ADMIN":
	                return ADMIN_PERMISSIONS;
	            case "MODERATOR":
	                return MODERATOR_PERMISSIONS;
	            case "USER":
	                return USER_PERMISSIONS;
	            default:
	                return Arrays.asList();
	        }
	    }

}
