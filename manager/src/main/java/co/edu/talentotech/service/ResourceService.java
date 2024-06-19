package co.edu.talentotech.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.talentotech.entity.Resource;
import co.edu.talentotech.repository.ProjectRepository;
import co.edu.talentotech.repository.ResourceRepository;
import co.edu.talentotech.response.MultipleResponse;
import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.ResponseMessages;
import co.edu.talentotech.response.SingleResponse;

@Service
public class ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ProjectRepository projectRepository;

	private static final Logger log = LoggerFactory.getLogger(ResourceService.class);

	/**
	 * Search all resources by a specific project id
	 * 
	 * @param projectId
	 * @return MultipleResponse
	 * @throws ResponseDetails
	 */
	public MultipleResponse getResourcesPerProject(Long projectId) throws ResponseDetails {
		MultipleResponse response = new MultipleResponse();
		try {
			List<Resource> resources = resourceRepository.findByProjectId(projectId);
			if (resources.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			} else {
				response.setData(resources);
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
	 * Create a new resource
	 * 
	 * @param resource
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse saveResource(Resource resource) throws ResponseDetails {
		try {
			if (resource.getId() != null) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ID);
			}
			if (resource.getProject() == null || resource.getProject().getId() == null
					|| resource.getProject().getId() <= 0) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_PROJECT_REQUIRED);
			}
			if (!projectRepository.existsById(resource.getProject().getId())) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_PROJECT_INVALID);
			}
			validateResource(resource);
			resource = resourceRepository.save(resource);
			SingleResponse response = new SingleResponse();
			response.setData(resource);
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
	 * Search for a specific resource by id
	 * 
	 * @param id
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse getResourceById(Long id) throws ResponseDetails {
		SingleResponse response = new SingleResponse();
		try {
			Optional<Resource> resource = resourceRepository.findById(id);
			if (resource.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			} else {
				response.setData(resource.get());
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
	 * Update data for a specific resource
	 * 
	 * @param resourceUpdated
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse updateResource(Resource resourceUpdated) throws ResponseDetails {
		SingleResponse response = new SingleResponse();
		try {
			if (resourceUpdated.getId() == null) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_ID_REQUIRED);
			}
			Optional<Resource> optionalResource = resourceRepository.findById(resourceUpdated.getId());
			if (optionalResource.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_NOT_EXIST);
			}
			validateResource(resourceUpdated);
			Resource resource = validateUpdateResource(optionalResource.get(), resourceUpdated);
			resourceRepository.save(resource);

			// create successful response
			response.setData(resource);
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
	 * Delete a resource by its id
	 * 
	 * @param id
	 * @return SingleResponse
	 * @throws ResponseDetails
	 */
	public SingleResponse deleteResource(Long id) throws ResponseDetails {
		try {
			Optional<Resource> resource = resourceRepository.findById(id);
			if (resource.isEmpty()) {
				throw new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_404);
			}
			resourceRepository.deleteById(id);
			SingleResponse response = new SingleResponse();
			response.setData(resource.get());
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

	private void validateResource(Resource resource) throws ResponseDetails {
		if (resource.getName() == null || resource.getName().isEmpty() || resource.getName().length() > 100) {
			throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_NAME);
		}
		if (resource.getDescription() != null) {
			if (resource.getDescription().length() > 250) {
				throw new ResponseDetails(ResponseMessages.CODE_400, ResponseMessages.ERROR_DESCRIPTION);
			}
		}

	}

	private Resource validateUpdateResource(Resource resourceExisting, Resource resourceNew) throws ResponseDetails {
		if (resourceNew.getName() != null) {
			resourceExisting.setName(resourceNew.getName());
		}
		if (resourceNew.getDescription() != null) {
			resourceExisting.setDescription(resourceNew.getDescription());
		}
		return resourceExisting;
	}
}
