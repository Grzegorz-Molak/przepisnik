package pw.paint.repository;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.model.User;


import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends MongoRepository<Recipe, ObjectId> {
    Optional<Recipe> findById(ObjectId id);

    Optional<Recipe> findByName(String name);

    @Query("{'name': { $regex: ?0, $options: 'i' }}")
    Page<Recipe> findByNameContaining(String keyword, Pageable pageable);

    @Query("{'tags': { $all: ?0 }}")
    Page<Recipe> findByTagsAll(List<DBRef> tags, Pageable pageable);

    @Query("{'name': { $regex: ?0, $options: 'i' }, 'tags': { $all: ?1 }}")
    Page<Recipe> findByTags(String keyword,List<DBRef> tags, Pageable pageable);

    List<Recipe> findByAuthor(User author);





}
