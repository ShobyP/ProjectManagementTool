package silver.ppmtool.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import silver.ppmtool.domain.Project;
import silver.ppmtool.services.MapValidationErrorService;
import silver.ppmtool.services.ProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(
            @Valid @RequestBody Project project,
            BindingResult result
    ){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null){
            return errorMap;
        }

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);

    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);
        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted.", HttpStatus.OK);
    }

    // for updating a project, we can use the database id and post it with updated information.
    // jpa is smart enough to understand that this is an update.
    // no put mapping is needed in that case.

}
