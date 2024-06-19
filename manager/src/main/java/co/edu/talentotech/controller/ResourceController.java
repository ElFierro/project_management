package co.edu.talentotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.talentotech.entity.Resource;
import co.edu.talentotech.response.MultipleResponse;
import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.SingleResponse;
import co.edu.talentotech.service.ResourceService;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/project/{projectId}")
    public MultipleResponse getResourcesPerProject(@PathVariable Long projectId) 
            throws ResponseDetails {
        MultipleResponse response = new MultipleResponse();
        try {
            response = resourceService.getResourcesPerProject(projectId);
        } catch (ResponseDetails e) { 
            response.setResponseDetails(e);
        }
        return response;
    }

    @PostMapping("/")
    public SingleResponse saveResource(@RequestBody Resource resource) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = resourceService.saveResource(resource);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    } 
    
    @GetMapping("/{id}")
    public SingleResponse getResourceById(@PathVariable Long id) 
            throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = resourceService.getResourceById(id);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }

    @PutMapping("/")
    public SingleResponse updateResource(@RequestBody Resource resource) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = resourceService.updateResource(resource);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }
    
    @DeleteMapping("/{id}")
    public SingleResponse deleteResource(@PathVariable Long id) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = resourceService.deleteResource(id);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }    
    
}
