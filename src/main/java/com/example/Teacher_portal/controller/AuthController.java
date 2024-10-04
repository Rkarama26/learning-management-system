package com.example.Teacher_portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Teacher_portal.Entity.Role;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.jwt.JwtProvider;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.LoginRequest;
import com.example.Teacher_portal.response.AuthResponse;
import com.example.Teacher_portal.service.impl.CustomUserServiceImpl;

@RestController
@RequestMapping("/home")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomUserServiceImpl customUserService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String phoneNumber = user.getPhoneNumber();
		Role role = user.getRole();
		if (role == null) {
			role = Role.STUDENT; // default role
		}

		if (!EnumUtils.isValidEnum(Role.class, role.name())) {
			throw new UserException("Invalid role provided");
		}

		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist != null) {
			throw new UserException("Email is already Used with Another Account");
		}

		if (password == null) {
			throw new UserException("Password can not be null");
		}

		// Set default role if not provided (optional, depending on your use case)
		if (role == null) {
			role = Role.STUDENT; // default role
		}

		User createdUser = new User();
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setEmail(email);
		createdUser.setPhoneNumber(phoneNumber);
		createdUser.setRole(role);
	

		User savedUser = userRepository.save(createdUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);

		String token = jwtProvider.generateToken(auth);

		AuthResponse authResponse = new AuthResponse(token, "Signup success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		// implemented this method below
		Authentication auth = authenticate(username, password);

		SecurityContextHolder.getContext().setAuthentication(auth);

		String token = jwtProvider.generateToken(auth);

		AuthResponse authResponse = new AuthResponse(token, "Signin success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	private Authentication authenticate(String username, String password) {

		UserDetails userDetails = customUserService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}

		User user = userRepository.findByEmail(username);

		// Retrieve the user's role as an enum
		Role role = user.getRole();

		// Create a list of authorities based on the user's role
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name())); // Use enum for role-based authority

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
