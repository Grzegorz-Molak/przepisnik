package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.model.User;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.TagRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper  recipeMapper;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final String defaultFolderName = "Moje przepisy";

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

    @Override
    public String createNewRecipe(RecipeDto recipeDto) {
        Optional<User> user = userRepository.findById(recipeDto.getAuthor_id());

        if(!user.isPresent()){
            return "Nie ma takiego użytkownika";
        }

        Folder defaultFolder = user.get().findFolderByName(defaultFolderName);
            List<Recipe> recipes = defaultFolder.getRecipes();
            for(Recipe recipe: recipes){
                if(recipe.getName().equals(recipeDto.getName())){
                    return "Przepis o takiej nazwie już istnieje";
                }
            }
        Recipe recipe = Recipe.builder()
                .id(recipeDto.getId())
                .name(recipeDto.getName())
                .ingredients(recipeDto.getIngredients())
                .steps(recipeDto.getSteps())
                .status(recipeDto.getStatus())
                .likes(recipeDto.getLikes())
                .timeMinutes(recipeDto.getTimeMinutes())
                .author(user.orElse(null))
                .build();
            defaultFolder.getRecipes().add(recipe);

        userRepository.save(user.get());


        return "Utworzono nowy folder";
    }
}
