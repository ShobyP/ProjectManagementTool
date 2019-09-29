package silver.ppmtool.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import silver.ppmtool.domain.ProjectTask;
import silver.ppmtool.services.MapValidationErrorService;
import silver.ppmtool.services.ProjectTaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult result,
            @PathVariable String backlog_id){

    ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
     if (errorMap != null) {
         return errorMap;
     }

     ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask);

     return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public List<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){

        return projectTaskService.findBacklogById(backlog_id);
    }

    @GetMapping("/{backlog_id}/{projectTask_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String projectTask_id){

        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, projectTask_id);
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);

    }

    @PatchMapping("/{backlog_id}/{projectTask_id}")
    public ResponseEntity<?> updateProjectTask(
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult result,
            @PathVariable String backlog_id,
            @PathVariable String projectTask_id
            ){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask,backlog_id,projectTask_id);

        return new ResponseEntity<ProjectTask> (updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{projectTask_id}")
    public ResponseEntity<?> deleteProjectTask(
            @PathVariable String backlog_id,
            @PathVariable String projectTask_id
    ){
        projectTaskService.deleteProjectTaskBySequence(backlog_id,projectTask_id);
        // write service functionality to delete a projectTask
        return new ResponseEntity<String> ("Project Task '"+ projectTask_id +"' was deleted sucessfully.", HttpStatus.OK);
    }
}
