package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import exceptions.UnmatchingIdsException;
import project.neki.model.User;
import project.neki.repository.SkillRepository;
import project.neki.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder enconder;

	@Autowired
	SkillRepository skillRepository;

	public UserService(UserRepository userRepository, PasswordEncoder enconder) {
		this.userRepository = userRepository;
		this.enconder = enconder;
	}

	public List<User> getAllUsers() {
		System.out.println("Entrei");
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Id de funcionario Invalido " + id));
	}

	public User saveUser(User User) {
		// if (User.getId() == null) {
		if (userRepository.existsById(User.getId()) == false) {
			User.setPassword(enconder.encode(User.getPassword()));
			return userRepository.save(User);
		} else {
			throw new UnmatchingIdsException("Id já Registrado " + User.getId());
		}

	}

	public User updateUser(User User) {

		if (userRepository.existsById(User.getId()) == true) {
			return userRepository.save(User);
		} else {
			throw new UnmatchingIdsException("id inexistente " + User.getId());
		}
	}

	public User validarSenha(String login, String password) {
		Optional<User> optUsuario = userRepository.findByLogin(login);
		System.out.println("Entrei");
		if (optUsuario.isEmpty()) {
			return null;
		}
		User usuario = optUsuario.get();

		// 4. Verificar se a senha fornecida corresponde à senha armazenada após a
		// codificação
		if (enconder.matches(password, usuario.getPassword())) {
			return usuario; // Senha correta, retorna o objeto User
		}

		return null;
	}
}
