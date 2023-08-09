package project.neki.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_skill")
public class UserSkill {

	@EmbeddedId
	private UserSkillId id = new UserSkillId();

	@ManyToOne
	@MapsId("userId")
	private User user;

	@ManyToOne
	@MapsId("skillId")
	private SkillModel skill;

	@Column(name = "level")
	private Long level;

	public UserSkill(User user, SkillModel skill, Long level) {
		this.user = user;
		this.skill = skill;
		this.level = level;
	}

}
