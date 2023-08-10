package project.neki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import project.neki.dtos.FuncionarioByIdDTO;
import project.neki.dtos.JwtResponseDTO;
import project.neki.dtos.LoginRequestDTO;
import project.neki.dtos.SignupRequestDTO;
import project.neki.repository.RoleRepository;
import project.neki.repository.UserRepository;
import security.jwt.JwtUtils;
import security.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")

public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public JwtResponseDTO authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
		return authService.registerUser(signUpRequest);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<FuncionarioByIdDTO> getById(@PathVariable Long id) {
		return authService.getById(id);
	}
}
