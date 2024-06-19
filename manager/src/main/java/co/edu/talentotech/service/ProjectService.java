package co.edu.talentotech.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.talentotech.entity.Project;
import co.edu.talentotech.entity.Resource;
import co.edu.talentotech.entity.Task;
import co.edu.talentotech.repository.ProjectRepository;
import co.edu.talentotech.repository.ResourceRepository;
import co.edu.talentotech.repository.TaskRepository;
import co.edu.talentotech.response.MultipleResponse;
import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.ResponseMessages;
import co.edu.talentotech.response.SingleResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private TaskRepository taskRepository;

	private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

	/**
	 * Gets all existing projects
	 * 
	 * @return MultipleResponse
	 * @throws ResponseDetails
	 */
	public MultipleResponse getAllProjects() throws ResponseDetails {
		MultipleResponse response = new MultipleResponse();
		try {
			List<Project> project = projectRepository.findAll();

			if (project.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			} else {
				response.setData(project);
				response.getResponseDetails().setCode(ResponseMessages.CODE_200);
				response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
				return response;
			}
		} catch (ResponseDetails e) {
			log.error(e.getCode(), e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * create a new project
	 * 
	 * @param project
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse saveProject(Project project) throws ResponseDetails {
		try {
			if (project.getId() != null) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ID);
			}
			validateProject(project);
			project = projectRepository.save(project);
			SingleResponse response = new SingleResponse();
			response.setData(project);
			response.getResponseDetails().setCode(ResponseMessages.CODE_200);
			response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
			return response;

		} catch (ResponseDetails e) {
			if (e.getCode().isEmpty() || e.getCode().isEmpty()) {
				e.setCode(ResponseMessages.CODE_400);
				e.setMessage(ResponseMessages.ERROR_400);
			}
			log.error(e.getCode(), e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Search for a specific project by id
	 * 
	 * @param id
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse getProjectById(Long id) throws ResponseDetails {
		SingleResponse response = new SingleResponse();
		try {
			Optional<Project> project = projectRepository.findById(id);

			if (project.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			} else {
				Project projectWithTasksAndResources = getProjectWithTasksAndResources(project.get());
				response.setData(projectWithTasksAndResources);
				response.getResponseDetails().setCode(ResponseMessages.CODE_200);
				response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
				return response;
			}
		} catch (ResponseDetails e) {
			log.error(e.getCode(), e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * update a specific project by id
	 * 
	 * @param projectUpdated
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse updateProject(Project projectUpdated) throws ResponseDetails {
		SingleResponse response = new SingleResponse();
		try {
			if (projectUpdated.getId() == null) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ID_REQUIRED);
			}
			Optional<Project> optionalProject = projectRepository.findById(projectUpdated.getId());
			if (optionalProject.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_NOT_EXIST);
			}
			validateProject(projectUpdated);
			Project project = validateUpdateProject(optionalProject.get(), projectUpdated);
			projectRepository.save(project);
			response.setData(project);
			response.getResponseDetails().setCode(ResponseMessages.CODE_200);
			response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
			return response;

		} catch (ResponseDetails e) {
			if (e.getCode().isEmpty() || e.getCode().isEmpty()) {
				e.setCode(ResponseMessages.CODE_400);
				e.setMessage(ResponseMessages.ERROR_400);
			}
			log.error(e.getCode(), e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * delete a specific project by id
	 * 
	 * @param id
	 * @return
	 * @throws ResponseDetails
	 */
	public SingleResponse deleteProject(Long id) throws ResponseDetails {
		try {
			Optional<Project> project = projectRepository.findById(id);
			if (project.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			}
			
			projectRepository.deleteById(id);
			SingleResponse response = new SingleResponse();
			response.setData(project.get());
			response.getResponseDetails().setCode(ResponseMessages.CODE_200);
			response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
			return response;
		} catch (ResponseDetails e) {
			if (e.getCode().isEmpty() || e.getCode().isEmpty()) {
				e.setCode(ResponseMessages.CODE_400);
				e.setMessage(ResponseMessages.ERROR_400);
			}
			log.error(e.getCode(), e.getMessage(), e);
			throw e;
		}
	}

	private Project validateUpdateProject(Project projectExisting, Project projectNew) {
		if (projectNew.getName() != null) {
			projectExisting.setName(projectNew.getName());
		}
		if (projectNew.getDescription() != null) {
			projectExisting.setDescription(projectNew.getDescription());
		}
		if (projectNew.getStartDate() != null) {
			projectExisting.setStartDate(projectNew.getStartDate());
		}
		if (projectNew.getEndDate() != null) {
			projectExisting.setEndDate(projectNew.getEndDate());
		}
		if (projectNew.getStatus() != null) {
			projectExisting.setStatus(projectNew.getStatus());
		}
		if (projectNew.getManager() != null) {
			projectExisting.setManager(projectNew.getManager());
		}
		return projectExisting;
	}

	private void validateProject(Project project) throws ResponseDetails {

		if (project.getName() == null || project.getName().isEmpty() || project.getName().length() > 100) {
			throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_NAME);
		}
		if (project.getDescription() != null) {
			if (project.getDescription().length() > 250) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_DESCRIPTION);
			}
		}

		if (project.getStatus() == null || project.getStatus().isEmpty()) {
			throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_STATUS_REQUIRED);
		} else {
			if (!project.getStatus().equals("Inicio") && !project.getStatus().equals("Planificación")
					&& !project.getStatus().equals("Ejecución") && !project.getStatus().equals("Supervisión")
					&& !project.getStatus().equals("Cierre")) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_STATUS_PROJECT);
			}
		}

		if (project.getStartDate() != null) {
			validateFormatDate(project.getStartDate());
		}

		if (project.getEndDate() != null) {
			validateFormatDate(project.getEndDate());
		}
		if (project.getManager() != null) {
			if (project.getManager().length() > 100) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_MANAGER);
			}
		}
	}

	private void validateFormatDate(LocalDate fecha) throws ResponseDetails {
		String dateFormat = "\\d{2}-\\d{2}-\\d{4}";
		if (!fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).matches(dateFormat)) {
			throw new ResponseDetails(ResponseMessages.CODE_404, "El formato de fecha debe ser 'dd-MM-yyyy'");
		}
	}

	private Project getProjectWithTasksAndResources(Project project) {
		List<Task> tasks = taskRepository.findByProjectId(project.getId());
		List<Resource> resources = resourceRepository.findByProjectId(project.getId());
		project.setTask(tasks);
		project.setResource(resources);
		return project;
	}
}