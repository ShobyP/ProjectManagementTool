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
}
