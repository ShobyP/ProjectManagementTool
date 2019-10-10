package silver.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import silver.ppmtool.domain.Backlog;
import silver.ppmtool.domain.Project;
import silver.ppmtool.domain.ProjectTask;
import silver.ppmtool.exceptions.ProjectNotFoundException;
import silver.ppmtool.repositories.BacklogRepository;
import silver.ppmtool.repositories.ProjectRepository;
import silver.ppmtool.repositories.ProjectTaskRepository;

import java.util.List;

@Service
public class ProjectTaskService {

    private Integer projectTaskSequence = 0;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try {
        /*
        PTs to be added to a specific project. projct != null, backlog exists.
        set the bl to pt
        project seq to be like projectIdentifier-1
        update the backlog seq
        initial priority set to null
        initial status when status is null
        handle exceptipons
         */

            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            projectTaskSequence = backlogSequence + projectTaskSequence + 1;
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + projectTaskSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

// priority needs fixing later.
            if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO DO");
            }

            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project not found");
        }

    }

    public List<ProjectTask> findBacklogById (String id){

        Project project = projectRepository.findByProjectIdentifier(id);
        if (project == null){
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist.");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlogID, String projectTaskSequence){
        // Make sure we are searching the correct backlog.

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlogID);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: '"+backlogID+"' does not exist.");
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectTaskSequence);
        if (projectTask == null){
            throw new ProjectNotFoundException("Project Task: '"+projectTaskSequence+"' does not exist.");
        }

        if(!projectTask.getProjectIdentifier().equals(backlogID)){
            throw new ProjectNotFoundException("Project Task: '"+
                    projectTaskSequence
                    +"' does not exist in Project: '"+
                    backlogID+"'.");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogID, String projectTaskID){

        ProjectTask projectTask = findProjectTaskByProjectSequence(backlogID,projectTaskID);
        // ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectTaskID);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deleteProjectTaskBySequence(String backlogID, String projectTaskID){

        ProjectTask projectTask = findProjectTaskByProjectSequence(backlogID,projectTaskID);
        projectTaskRepository.delete(projectTask);
    }

}
