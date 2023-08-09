package project.neki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.neki.model.SkillModel;
import project.neki.model.User;
import project.neki.model.UserSkill;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    Optional<UserSkill> findByUserAndSkill(User funcionario, SkillModel skill);

    boolean existsByUserAndSkill(User funcionario, SkillModel skill);
}
