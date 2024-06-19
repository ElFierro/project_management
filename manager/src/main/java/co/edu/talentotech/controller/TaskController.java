package co.edu.talentotech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.talentotech.entity.Task;
import co.edu.talentotech.response.MultipleResponse;
import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.SingleResponse;
import co.edu.talentotech.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
    @Autowired
    private TaskService taskService;

    @GetMapping("/project/{projectId}")
    public MultipleResponse getTasksPerProject( @PathVariable Long projectId) 
            throws ResponseDetails {
    	MultipleResponse response = new MultipleResponse();
        try {
            response = taskService.getTasksPerProject(projectId);
        } catch (ResponseDetails e) { 
        	response.setResponseDetails(e);
        }
        return response;
    }

    @PostMapping("/")
    public SingleResponse saveTask( @RequestBody Task task) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response =taskService.saveTask(task);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    } 
    
    @GetMapping("/{id}")
    public SingleResponse getTaskById( @PathVariable Long id) 
            throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = taskService.getTaskById(id);
        } catch (ResponseDetails e) {
        	response.setResponseDetails(e);
        }
        return response;
    }

    @PutMapping("/")
    public SingleResponse updateTask( @RequestBody Task task) throws ResponseDetails {
            SingleResponse response = new SingleResponse();
        try {
            response = (SingleResponse) taskService.updateTask(task);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }
    
    
    @DeleteMapping("/{id}")
    public SingleResponse deleteTask(@PathVariable Long id) throws ResponseDetails {
        SingleResponse response = new SingleResponse();
        try {
            response = taskService.deleteTask(id);
        } catch (ResponseDetails e) {
            response.setResponseDetails(e);
        }
        return response;
    }    
    
}
