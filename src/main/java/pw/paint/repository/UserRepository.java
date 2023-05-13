package pw.paint.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    //User findByUserNameNotOptional (String username);
}
