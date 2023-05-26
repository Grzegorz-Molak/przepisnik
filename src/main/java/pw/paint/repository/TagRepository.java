package pw.paint.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {
    Tag findByName(String names);
}
