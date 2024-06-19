package co.edu.talentotech.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.talentotech.entity.Task;
import co.edu.talentotech.repository.ProjectRepository;
import co.edu.talentotech.repository.TaskRepository;
import co.edu.talentotech.response.MultipleResponse;
import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.ResponseMessages;
import co.edu.talentotech.response.SingleResponse;
import co.edu.talentotech.util.EmailValidator;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
    @Autowired
    private NotificationService notificationService;

	private static final Logger log = LoggerFactory.getLogger(TaskService.class);

	/**
	 * Search all tasks by a specific project id
	 * 
	 * @param projectId
	 * @return MultipleResponse
	 * @throws ResponseDetails
	 */
	public MultipleResponse getTasksPerProject(Long projectId) throws ResponseDetails {
		MultipleResponse response = new MultipleResponse();
		try {
			List<Task> tasks = taskRepository.findByProjectId(projectId);
			if (tasks.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			} else {
				response.setData(tasks);
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
	 * Create a new task
	 * 
	 * @param task
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse saveTask(Task task) throws ResponseDetails {
		try {
			if (task.getId() != null) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ID);
			}
			if (task.getProject() == null || task.getProject().getId() == null || task.getProject().getId() <= 0) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_PROJECT_REQUIRED);
			}
			if (!projectRepository.existsById(task.getProject().getId())) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_PROJECT_INVALID);
			}
			validateTask(task);
			task = taskRepository.save(task);
			SingleResponse response = new SingleResponse();
			response.setData(task);
			response.getResponseDetails().setCode(ResponseMessages.CODE_200);
			response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
			if(task.getAssigned()!=null) {
				 notificationService.sendNotification(
			        		task.getAssigned(),
			                "Nueva tarea asignada",
			                "Tiene la siguiente tarea asignada: " + task.getName());
			}
	       
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
	 * Search for a specific task by id
	 * 
	 * @param id
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse getTaskById(Long id) throws ResponseDetails {
		SingleResponse response = new SingleResponse();
		try {
			Optional<Task> waste = taskRepository.findById(id);
			if (waste.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			} else {
				response.setData(waste.get());
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
	 * Update data for a specific task
	 * 
	 * @param taskUpdated
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse updateTask(Task taskUpdated) throws ResponseDetails {
		SingleResponse response = new SingleResponse();
		try {
			if(taskUpdated.getId()== null) {
        		throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ID_REQUIRED);
        	}
			Optional<Task> optionalTask = taskRepository.findById(taskUpdated.getId());
			if (optionalTask.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_NOT_EXIST);
			}
			validateTask(taskUpdated);
			Task task = validateUpdateTask(optionalTask.get(), taskUpdated);
			taskRepository.save(task);

			// create successful response
			response.setData(task);
			response.getResponseDetails().setCode(ResponseMessages.CODE_200);
			response.getResponseDetails().setMessage(ResponseMessages.ERROR_200);
			if(task.getAssigned()!= null) {
				 notificationService.sendNotification(
			        		task.getAssigned(),
			                "Tarea modificada",
			                "Se hicieron cambios sobre la siguiente tarea: " + task.getName());
			}
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
	 * Delete a task by its id
	 * 
	 * @param id
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse deleteTask(Long id) throws ResponseDetails {
		try {
			Optional<Task> task = taskRepository.findById(id);
			if (task.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_400);
			}
			taskRepository.deleteById(id);
			SingleResponse response = new SingleResponse();
			response.setData(task.get());
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

	private void validateFormatDate(LocalDate fecha) throws ResponseDetails {
		String dateFormat = "\\d{2}-\\d{2}-\\d{4}";
		if (!fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).matches(dateFormat)) {
			throw new ResponseDetails(ResponseMessages.CODE_404, "El formato de fecha debe ser 'dd-MM-yyyy'");
		}
	}

	private void validateTask(Task task) throws ResponseDetails {

		if (task.getName() == null || task.getName().isEmpty() || task.getName().length() > 100) {
			throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_NAME);
		}

		if(task.getDescription()!= null) {
			if (task.getDescription().length() > 250) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_DESCRIPTION);
			}
		}
		
		if (task.getStatus() == null || task.getStatus().isEmpty()) {
			throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_STATUS_REQUIRED);
		} else {
			if (!task.getStatus().equals("Pendiente") && !task.getStatus().equals("En progreso")
					&& !task.getStatus().equals("Completado")) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_STATUS);
			}
		}

		if (task.getStartDate() != null) {
			validateFormatDate(task.getStartDate());
		}

		if (task.getEndDate() != null) {
			validateFormatDate(task.getEndDate());
		}
		
		if(task.getAssigned()!= null) {
			if(task.getAssigned().length() > 100) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ASSIGNED);
			}
		    if (!EmailValidator.isValidEmail(task.getAssigned())) {
		        throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_EMAIL);
		    }
		}
		
	}

	private Task validateUpdateTask(Task taskExisting, Task taskNew) throws ResponseDetails {
		if (taskNew.getName() != null) {
			taskExisting.setName(taskNew.getName());
		}
		if (taskNew.getDescription() != null) {
			taskExisting.setDescription(taskNew.getDescription());
		}
		if (taskNew.getStartDate() != null) {
			taskExisting.setStartDate(taskNew.getStartDate());
		}
		if (taskNew.getEndDate() != null) {
			taskExisting.setEndDate(taskNew.getEndDate());
		}
		if (taskNew.getStatus() != null) {
			taskExisting.setStatus(taskNew.getStatus());
		}
		if (taskNew.getAssigned() != null) {
			taskExisting.setAssigned(taskNew.getAssigned());
		}
		return taskExisting;
	}

}
