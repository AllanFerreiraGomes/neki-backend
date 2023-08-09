package project.neki.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioSkillListDTO {
    
	private Long funcionarioId;
	private List<Long> skillIds;
    private Long level;

}
