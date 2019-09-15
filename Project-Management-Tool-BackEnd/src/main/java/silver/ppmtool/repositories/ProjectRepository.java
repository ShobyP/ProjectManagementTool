package silver.ppmtool.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import silver.ppmtool.domain.Project;

import java.util.Optional;


@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();



}

