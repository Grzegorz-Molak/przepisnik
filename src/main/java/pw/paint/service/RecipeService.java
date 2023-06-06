package pw.paint.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import pw.paint.DTOs.model.RecipeDto;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> getAllRecipes();

    List<String> getAllTags();

    String createNewRecipe(RecipeDto recipeDto);

    List<RecipeDto> search(String author, String keyword, List<String> tags, Pageable pageable);

    RecipeDto getRecipeById(ObjectId id_);
}
