package silver.ppmtool.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import silver.ppmtool.domain.Backlog;
import silver.ppmtool.domain.Project;
import silver.ppmtool.exceptions.ProjectIdException;
import silver.ppmtool.repositories.BacklogRepository;
import silver.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        try {
            project.setProjectIdentifier(projectIdentifier);

           /*
           if project id is null, that means, its a new project. if its not null, that means
           its an updating project. We only want to create backlog when its a new project.
           if so, create a new backlog
           set project backlog to the new backlog
           set backlog project to the new project.
           set backlog projectIdentifier as same as the new project (in upper case).
            */
           if (project.getId() == null){
               Backlog backlog = new Backlog();
               project.setBacklog(backlog);
               backlog.setProject(project);
               backlog.setProjectIdentifier(projectIdentifier);
           }
           if (project.getId() != null){
               project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
           }

           return projectRepository.save(project);
         }catch (Exception e){
           throw new ProjectIdException
                   ("Project ID '"+projectIdentifier+"' already exists.");
       }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null){
            throw new ProjectIdException
                    ("Project ID '"+projectId+"' does not exist.");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null){
            throw new ProjectIdException("Cannot delete with Project ID '"+projectId+"', " +
                    "because it does not exist.");
        }

        projectRepository.delete(project);

    }


}
