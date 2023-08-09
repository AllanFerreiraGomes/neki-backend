package project.neki.dtos;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
	
    private Long id;

	private String name;

	private Set<String> role;

    private String login;
	
	private String password;

}