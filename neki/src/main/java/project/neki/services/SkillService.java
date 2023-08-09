package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exceptions.UnmatchingIdsException;
import project.neki.model.SkillModel;
import project.neki.repository.SkillRepository;

@Service
public class SkillService {

	@Autowired
	 SkillRepository SkillRepository;

	
	public List<SkillModel> getAllSkillModels() {
		return SkillRepository.findAll();
	}

	public SkillModel getSkillModelById(Long id) {
		return SkillRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id Invalido" + id));
	}

	public SkillModel saveSkillModel(SkillModel SkillModel) {
		// if (SkillModel.getId() == null) {
		if (SkillRepository.existsById(SkillModel.getId()) == false) {
			return SkillRepository.save(SkillModel);
		} else {
			throw new UnmatchingIdsException("Id já Registrado " + SkillModel.getId());
		}
	
	}

	public SkillModel updateSkillModel(SkillModel SkillModel) {

		if (SkillRepository.existsById(SkillModel.getId()) == true) {
			return SkillRepository.save(SkillModel);
		} else {
			throw new UnmatchingIdsException("id inexistente " + SkillModel.getId());
		}
	}
	
}
