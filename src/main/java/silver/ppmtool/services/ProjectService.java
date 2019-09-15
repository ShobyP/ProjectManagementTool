package silver.ppmtool.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import silver.ppmtool.domain.Project;
import silver.ppmtool.exceptions.ProjectIdException;
import silver.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        //Logic here
        // using my custom project exception service.
       try {
           project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
           return projectRepository.save(project);
         }catch (Exception e){
           throw new ProjectIdException
                   ("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists.");
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
