package pw.paint.DTOs.mappers;

import org.springframework.stereotype.Component;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeMapper {

    public static RecipeDto toRecipeDto(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId().toString())
                .name(recipe.getName())
                .ingredients(recipe.getIngredients())
                .steps(recipe.getSteps())
                .status(recipe.getStatus())
                .timeMinutes(recipe.getTimeMinutes())
                .author(recipe.getAuthor().getUsername())
                .tags(getTags(recipe.getTags()))
                .build();
    }

    private static List<String> getTags(List<Tag> tags) {
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        return tagNames;
    }

    public static List<RecipeDto> toRecipeDto(List<Recipe> recipes) {
        List<RecipeDto> recipesDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipesDto.add(toRecipeDto(recipe));
        }
        return recipesDto;
    }

}
