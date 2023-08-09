package project.neki.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import project.neki.dtos.MessageResponseDTO;
import project.neki.dtos.SignupRequestDTO;
import project.neki.model.Role;
import project.neki.model.RoleEnum;
import project.neki.model.User;
import project.neki.repository.RoleRepository;
import project.neki.repository.UserRepository;
import security.jwt.JwtUtils;
import security.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
	private AuthenticationManager authenticationManager;

	// Não deixe o Controller acessar o repository, faça no Service

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin") // Valid - verifica se os dados são válidos
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
		if (userRepository.existsByName(signUpRequest.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Username já utilizado!"));
		}

		if (userRepository.existsByLogin(signUpRequest.getLogin())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Login já utilizado!"));
		}

		// Converta as strings de papéis em objetos Role
		List<Role> roles = new ArrayList<>();
		if (signUpRequest.getRole() != null) {
			for (String roleName : signUpRequest.getRole()) {
				RoleEnum roleEnum = RoleEnum.valueOf(roleName);
				Role roleEntity = roleRepository.findByName(roleEnum)
						.orElseThrow(() -> new RuntimeException("Erro: Role não encontrada."));
				roles.add(roleEntity);
			}
		}

		// Crie o usuário com os dados e papéis definidos
		User user = new User(signUpRequest.getId(), signUpRequest.getName(), signUpRequest.getLogin(),
				encoder.encode(signUpRequest.getPassword()), new HashSet<>(roles));

		// Salve o usuário no banco de dados
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponseDTO("Usuário registrado com sucesso!"));
	}

	 @GetMapping("/get/{id}")
	    public ResponseEntity<FuncionarioByIdDTO> getById(@PathVariable Long id) {
	        Optional<User> userOptional = userRepository.findById(id);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            FuncionarioByIdDTO funcionarioDTO = new FuncionarioByIdDTO();
	            funcionarioDTO.setId(user.getId());
	            funcionarioDTO.setName(user.getName());
	            funcionarioDTO.setLogin(user.getLogin());
	            return ResponseEntity.ok(funcionarioDTO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
}
