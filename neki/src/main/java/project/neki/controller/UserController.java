package project.neki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.neki.dtos.AssociarSkillsDTO;
import project.neki.dtos.CredenciaisDTO;
import project.neki.dtos.SkillInfoDTO;
import project.neki.model.User;
import project.neki.services.UserService;

@RestController
@RequestMapping("/funcionarios")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println("Bati");
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User User) {
		User UserResponse = userService.saveUser(User);
		if (UserResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(UserResponse, HttpStatus.OK);
		}
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User User) {

		User UserResponse = userService.updateUser(User);
		if (UserResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(UserResponse, HttpStatus.OK);
		}
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteUser(@PathVariable String id) {
//		Boolean response = userService(id);
//		if (response) {
//			return ResponseEntity.ok("User deletado com Sucesso!");
//
//		} else {
//			return ResponseEntity.badRequest().body("Deu ruim");
//		}
//	}

	@PostMapping("/validar-senha")
	public Object validarSenha(@RequestBody CredenciaisDTO credenciais) {
		String login = credenciais.getLogin();
		String senha = credenciais.getPassword();

		User funcionario = userService.validarSenha(login, senha);
		if (funcionario != null) {
			return funcionario; // Retorna o objeto User se as credenciais estiverem corretas
		} else {
			return false; // Retorna false se as credenciais estiverem erradas
		}
	}
}
