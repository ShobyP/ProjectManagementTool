package silver.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import silver.ppmtool.domain.Backlog;
import silver.ppmtool.domain.ProjectTask;
import silver.ppmtool.repositories.BacklogRepository;
import silver.ppmtool.repositories.ProjectTaskRepository;

import java.util.List;

@Service
public class ProjectTaskService {

    private Integer projectTaskSequence = 0;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

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
        projectTaskSequence = backlogSequence+projectTaskSequence+1;
        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-"+ projectTaskSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

// priority needs fixing later.
        if(projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }

        if (projectTask.getStatus() == "" || projectTask.getStatus() == null){
            projectTask.setStatus("TO DO");
        }

        return projectTaskRepository.save(projectTask);

    }

    public List<ProjectTask> findBacklogById (String id){
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

}
