package pw.paint.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, ObjectId> {
}
