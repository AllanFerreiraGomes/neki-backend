package project.neki.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FuncionarioByIdDTO {

	private Long id;
	private String name;
	private String login;
}