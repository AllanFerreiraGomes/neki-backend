package project.neki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.neki.model.User;

public interface UserRepository extends JpaRepository<User , Long>{

	Optional<User> findByLogin(String login);

    Optional<User> findByName(String name);

	boolean existsByName(String name);

	boolean existsByLogin(String login);
	
}
