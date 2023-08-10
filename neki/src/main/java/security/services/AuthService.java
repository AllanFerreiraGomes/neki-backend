package security.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

@Service
public class AuthService {

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

	public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
System.out.println("loginRequest.getLogin()"  + loginRequest.getLogin());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponseDTO(jwt, userDetails.getId(), userDetails.getUsername(), roles);
	}

	public ResponseEntity<?> registerUser(SignupRequestDTO signUpRequest) {
		if (userRepository.existsByName(signUpRequest.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Username já utilizado!"));
		}

		if (userRepository.existsByLogin(signUpRequest.getLogin())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Login já utilizado!"));
		}

		List<Role> roles = new ArrayList<>();
		if (signUpRequest.getRole() != null) {
			for (String roleName : signUpRequest.getRole()) {
				RoleEnum roleEnum = RoleEnum.valueOf(roleName);
				Role roleEntity = roleRepository.findByName(roleEnum)
						.orElseThrow(() -> new RuntimeException("Erro: Role não encontrada."));
				roles.add(roleEntity);
			}
		}

		User user = new User(signUpRequest.getId(), signUpRequest.getName(), signUpRequest.getLogin(),
				encoder.encode(signUpRequest.getPassword()), new HashSet<>(roles));

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponseDTO("Usuário registrado com sucesso!"));
	}

	public ResponseEntity<FuncionarioByIdDTO> getById( Long id) {
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
