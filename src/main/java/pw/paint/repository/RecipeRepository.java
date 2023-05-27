package pw.paint.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.Recipe;

import java.util.Optional;

public interface RecipeRepository extends MongoRepository<Recipe, ObjectId> {
    Optional<Recipe> findById(ObjectId id);
}
