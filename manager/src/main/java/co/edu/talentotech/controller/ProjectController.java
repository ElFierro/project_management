package co.edu.talentotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.talentotech.entity.Project;
import co.edu.talentotech.response.MultipleResponse;
import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.SingleResponse;
import co.edu.talentotech.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public MultipleResponse getAllProjects() throws ResponseDetails {
    	MultipleResponse response = new MultipleResponse();
        try {
            response = projectService.getAllProjects();
        } catch (ResponseDetails e) { 
        	response.setResponseDetails(e);
        }
        return response;
    }
    
    @PostMapping("/")
    public SingleResponse saveProject(@RequestBody Project project) throws ResponseDetails {
    	SingleResponse response = new SingleResponse();
        try {
            response =projectService.saveProject(project);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    } 
    
    
    @GetMapping("/{id}")
    public SingleResponse getProjectById( @PathVariable Long id) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = projectService.getProjectById(id);
        } catch (ResponseDetails e) {
        	response.setResponseDetails(e);
        }
        return response;
    }

    @PutMapping("/")
    public SingleResponse updateProject( @RequestBody Project updatedProject) throws ResponseDetails {
            SingleResponse response = new SingleResponse();
        try {
            response = (SingleResponse) projectService.updateProject(updatedProject);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public SingleResponse deleteProject(@PathVariable Long id) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = projectService.deleteProject(id);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }   

}