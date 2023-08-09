package project.neki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.neki.dtos.FuncionarioByIdDTO;
import project.neki.dtos.FuncionarioSkillDTO;
import project.neki.dtos.FuncionarioSkillListDTO;
import project.neki.dtos.SkillIdDTO;
import project.neki.dtos.SkillInfoDTO;
import project.neki.services.UserSkillService;

@RestController
@RequestMapping("/funcionarios/{funcionarioId}/skills")
public class UserSkillController {
	
	@Autowired
	 UserSkillService userSkillService;
	
	   @GetMapping
	    public FuncionarioByIdDTO getById(@PathVariable Long id) {
	        return userSkillService.getById(id);
	    }

	@PostMapping("/associar-skills")
	public ResponseEntity<Void> associarSkillsAoUser(@PathVariable Long funcionarioId,
			@RequestBody FuncionarioSkillListDTO funcionarioSkillListDTO) {
		userSkillService.associarSkillUser(funcionarioId, funcionarioSkillListDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	

	@PutMapping("/atualizar")
	public ResponseEntity<Void> atualizarNivelSkillDoUser(@PathVariable Long funcionarioId,
			@RequestBody FuncionarioSkillDTO skillDTO) {
		userSkillService.atualizarNivelSkillDoUser(funcionarioId, skillDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<Void> excluirAssociacaoSkillDoUser(@PathVariable Long funcionarioId,
			@RequestBody SkillIdDTO skillIdDTO) {
		userSkillService.excluirAssociacaoSkillDoUser(funcionarioId, skillIdDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/listar")
	public ResponseEntity<List<SkillInfoDTO>> listarSkillsUser(@PathVariable Long funcionarioId) {
		List<SkillInfoDTO> skills = userSkillService.listarSkillsUser(funcionarioId);
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}
}
