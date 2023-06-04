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

        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setStatus(recipe.getStatus());
        recipeDto.setLikes(recipe.getLikes());
        recipeDto.setTimeMinutes(recipe.getTimeMinutes());
        recipeDto.setAuthor(recipe.getAuthor().getUsername());
        List<String> tags = new ArrayList<>();
        for(Tag tag:recipe.getTags()){
            tags.add(tag.getName());
        }
        recipeDto.setTags(tags);
        return recipeDto;
    }

    public static List<RecipeDto> toRecipeDto(List<Recipe> recipes) {
        List<RecipeDto> recipesDto = new ArrayList<>();

        for(Recipe recipe : recipes){
            recipesDto.add(toRecipeDto(recipe));
        }

        return recipesDto;
    }

    }
