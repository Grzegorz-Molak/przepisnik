package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper  recipeMapper;
    private final TagRepository tagRepository;

    @Override
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDto> recipeDtos= new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDtos.add(recipeMapper.toRecipeDto(recipe));
        }
        return recipeDtos;

    }

    @Override
    public List<String> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        tags = tagRepository.findAll();

        List<String> tags_s = new ArrayList<>();

        for(Tag tag :tags){
            tags_s.add(tag.getName());
        }
        return  tags_s;

    }
}
