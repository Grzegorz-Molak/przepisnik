package pw.paint.service;

import pw.paint.DTOs.model.RecipeDto;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> getAllRecipes();

    List<String> getAllTags();

    String createNewRecipe(RecipeDto recipeDto);
}
