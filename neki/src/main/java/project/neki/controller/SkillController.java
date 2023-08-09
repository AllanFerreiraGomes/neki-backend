package project.neki.controller;

import java.util.List;

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

import project.neki.model.SkillModel;
import project.neki.services.SkillService;


@RestController
@RequestMapping("/skill")
public class SkillController {


	@Autowired
	SkillService SkillService;
	
	@GetMapping
	public ResponseEntity<List<SkillModel>> getAllSkillModels() {
		return new ResponseEntity<>(SkillService.getAllSkillModels(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SkillModel> getSkillModelById( @PathVariable Long id) {
		return new ResponseEntity<>(SkillService.getSkillModelById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<SkillModel> saveSkillModel(@RequestBody SkillModel SkillModel) {
		SkillModel SkillModelResponse = SkillService.saveSkillModel(SkillModel);
		if (SkillModelResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(SkillModelResponse, HttpStatus.OK);
		}
	}

	@PutMapping
	public ResponseEntity<SkillModel> updateSkillModel(@RequestBody SkillModel SkillModel) {

		SkillModel SkillModelResponse = SkillService.updateSkillModel(SkillModel);
		if (SkillModelResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(SkillModelResponse, HttpStatus.OK);
		}
	}


//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteSkillModel(@PathVariable String id) {
//		Boolean response = SkillService(id);
//		if (response) {
//			return ResponseEntity.ok("SkillModel deletado com Sucesso!");
//
//		} else {
//			return ResponseEntity.badRequest().body("Deu ruim");
//		}
//	}

//	@PostMapping("/login")
//	public ResponseEntity<SkillModel> login(@RequestBody LoginRequestDTO loginRequest) {
//
//		String login = loginRequest.getLogin();
//		String password = loginRequest.getPassword();
//
//		SkillModel authenticatedSkillModel = SkillService.login(login, password);
//
//		if (authenticatedSkillModel != null) {
//			return ResponseEntity.ok(authenticatedSkillModel);
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//		}
//	}

	
}

