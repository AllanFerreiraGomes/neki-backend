package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.neki.dtos.FuncionarioByIdDTO;
import project.neki.dtos.FuncionarioSkillDTO;
import project.neki.dtos.FuncionarioSkillListDTO;
import project.neki.dtos.SkillIdDTO;
import project.neki.dtos.SkillInfoDTO;
import project.neki.model.SkillModel;
import project.neki.model.User;
import project.neki.model.UserSkill;
import project.neki.repository.SkillRepository;
import project.neki.repository.UserRepository;
import project.neki.repository.UserSkillRepository;

@Service
public class UserSkillService {

	private final UserSkillRepository userSkillRepository;
	private final UserRepository userRepository;
	private final SkillRepository skillRepository;

	@Autowired
	public UserSkillService(UserSkillRepository userSkillRepository, UserRepository userRepository,
			SkillRepository skillRepository) {
		super();
		this.userSkillRepository = userSkillRepository;
		this.userRepository = userRepository;
		this.skillRepository = skillRepository;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Transactional
	public void associarSkillAoUser(User user, FuncionarioSkillDTO skillDTO) {
		SkillModel skill = skillRepository.findById(skillDTO.getSkillId())
				.orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

		UserSkill userSkill = new UserSkill(user, skill, skillDTO.getLevel());
		userSkillRepository.save(userSkill);
	}

	public Boolean associarSkillUser(Long userId, FuncionarioSkillListDTO userSkillListDTO) {
		User userModel = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Id do user não válido"));

		System.out.println("NOME DO FUNCIONARIO " + userModel.getName());

		for (Long skillId : userSkillListDTO.getSkillIds()) {
			SkillModel skill = skillRepository.findById(skillId)
					.orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

			System.out.println("NOME DA SKILL " + skillId);

			// Verifica se a associação já existe
			boolean associationExists = userSkillRepository.existsByUserAndSkill(userModel, skill);
			if (!associationExists) {
				System.out.println("ENTREIII");
				UserSkill userSkill = new UserSkill(userModel, skill, userSkillListDTO.getLevel());

				System.out.println("2");
				userSkillRepository.save(userSkill);

				System.out.println("3");
			}
		}

		return true;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Transactional
	public void atualizarNivelSkillDoUser(Long userId, FuncionarioSkillDTO skillDTO) {
		User user = getUserById(userId);
		SkillModel skill = getSkillById(skillDTO.getSkillId());

		UserSkill userSkill = userSkillRepository.findByUserAndSkill(user, skill)
				.orElseThrow(() -> new NoSuchElementException("Associação entre funcionário e skill não encontrada."));

		userSkill.setLevel(skillDTO.getLevel());
		userSkillRepository.save(userSkill);
	}

	@Transactional
	public void excluirAssociacaoSkillDoUser(Long userId, SkillIdDTO skillIdDTO) {
		User user = getUserById(userId);
		SkillModel skill = getSkillById(skillIdDTO.getSkillId());

		UserSkill userSkill = userSkillRepository.findByUserAndSkill(user, skill)
				.orElseThrow(() -> new NoSuchElementException("Associação entre funcionário e skill não encontrada."));

		user.getFuncionarioSkills().remove(userSkill);
		skill.getFuncionarioSkills().remove(userSkill);
		userSkillRepository.delete(userSkill);
	}

	private User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found"));
	}

	private SkillModel getSkillById(Long skillId) {
		return skillRepository.findById(skillId)
				.orElseThrow(() -> new NoSuchElementException("Skill with ID " + skillId + " not found"));
	}

	@Transactional(readOnly = true)
	public List<SkillInfoDTO> listarSkillsUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Id: [" + userId + "] do user não válido"));

		return user
				.getFuncionarioSkills().stream().map(userSkill -> new SkillInfoDTO(userSkill.getSkill().getId(),
						userSkill.getSkill().getName(), userSkill.getLevel(), userSkill.getSkill().getUrlImagem()))
				.collect(Collectors.toList());
	}

	public FuncionarioByIdDTO getById(Long userId) {
		User userModel = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Id do usuário não válido"));

		FuncionarioByIdDTO dto = new FuncionarioByIdDTO();
		dto.setId(userModel.getId());
		dto.setName(userModel.getName());
		dto.setLogin(userModel.getLogin());

		return dto;
	}
}
