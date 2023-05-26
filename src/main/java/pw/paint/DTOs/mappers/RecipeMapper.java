package pw.paint.DTOs.mappers;

import org.springframework.stereotype.Component;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.model.Recipe;

@Component
public class RecipeMapper {

    public static RecipeDto toRecipeDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setStatus(recipe.getStatus());
        recipeDto.setLikes(recipe.getLikes());
        recipeDto.setTimeMinutes(recipe.getTimeMinutes());
        recipeDto.setAuthor_id(recipe.getAuthor().getId());
        return recipeDto;
    }
}
