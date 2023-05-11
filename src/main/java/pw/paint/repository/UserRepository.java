package pw.paint.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.User;

public interface UserRepository extends MongoRepository<User, Integer> {
}
