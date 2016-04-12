package embedded.mongo.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "test", path = "test")
public interface TestRepository extends MongoRepository<Test, String> {

    Optional<Test> findByVal(@Param("val") String val);
}
