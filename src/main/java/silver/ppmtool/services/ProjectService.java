package silver.ppmtool.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import silver.ppmtool.domain.Project;
import silver.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        //Logic later.

       return projectRepository.save(project);
    }
}
