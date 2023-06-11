package pw.paint.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.DTOs.requests.NewRecipeRequest;

import java.util.List;

public interface RecipeService {
   // List<RecipeDto> getAllRecipes();

    List<String> getAllTags();

    ObjectId createNewRecipe(NewRecipeRequest newRecipeRequest);

    List<ShortRecipeDto> search(String author, String keyword, List<String> tags, Boolean status, Pageable pageable);

    RecipeDto getRecipeById(ObjectId id_);

    void deleteRecipe(ObjectId objectId);

    void changeStatus(String id);

    byte[] getImage(ObjectId objectId);
}
