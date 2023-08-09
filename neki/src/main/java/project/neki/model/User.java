package project.neki.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

@Table(name = "users")
@JsonIgnoreProperties("userSkills")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(unique = true)
    private String login;

    @Column(name = "password")
    private String password;
    

	public User(Long id, String name, String login, String password, Set<Role> set) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.roles = set;
	}
  
    
    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserSkill> funcionarioSkills = new ArrayList<>();

    public boolean hasSkill(Long skillId) {
        return funcionarioSkills.stream()
                .anyMatch(funcionarioSkill -> funcionarioSkill.getSkill().getId().equals(skillId));
    }
    
    public void addUserSkill(UserSkill funcionarioSkill) {
        funcionarioSkills.add(funcionarioSkill);
        funcionarioSkill.setUser(this);
    }

    public void removeUserSkill(UserSkill funcionarioSkill) {
        funcionarioSkills.remove(funcionarioSkill);
        funcionarioSkill.setUser(null);
    }

}
