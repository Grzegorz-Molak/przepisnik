package pw.paint.service;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    private final RecipeMapper recipeMapper;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final String defaultFolderName = "Moje przepisy";

    @Override
    public List<RecipeDto> getAllRecipes() {

        return recipeMapper.toRecipeDto(recipeRepository.findAll());

    }

    @Override
    public List<String> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        tags = tagRepository.findAll();

        List<String> tags_s = new ArrayList<>();

        for (Tag tag : tags) {
            tags_s.add(tag.getName());
        }
        return tags_s;

    }

    @Override
    public String createNewRecipe(RecipeDto recipeDto) {
        Optional<User> user = userRepository.findByUsername(recipeDto.getAuthor());

        if (!user.isPresent()) {
            return "Nie ma takiego użytkownika";
        }

        Folder defaultFolder = user.get().findFolderByName(defaultFolderName);
        List<Recipe> recipes = defaultFolder.getRecipes();
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(recipeDto.getName())) {
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

        recipeRepository.save(recipe);
        userRepository.save(user.get());


        return "Utworzono nowy folder";
    }

    @Override
    public List<RecipeDto> search(String author, String keyword, List<String> tags, Pageable pageable) {

        if (keyword.isBlank()&&tags.isEmpty()&&author.isBlank()) {
            return recipeMapper.toRecipeDto(recipeRepository.findAll(pageable).getContent());
        }

        List<DBRef> tagsRef = new ArrayList<>();
        Optional<Tag> tag;
        for (String tagName : tags) {
            tag = tagRepository.findByName(tagName);
            if (tag.isPresent()) {
                tagsRef.add(new DBRef("tags", tag.get().getId()));
            }
        }

        Optional<User> userWithId = userRepository.findByUsername(author);
        DBRef authorRef = null;
        if(userWithId.isPresent()){
            authorRef = new DBRef("users", userWithId.get().getId());
        }

        if (keyword.isBlank()&&tags.isEmpty()) {
            return recipeMapper.toRecipeDto(recipeRepository.findByAuthor(authorRef, pageable).getContent());
        }

        if (keyword.isBlank()&&author.isBlank()) {
            return recipeMapper.toRecipeDto(recipeRepository.findByTagsAll(tagsRef, pageable).getContent());
        }

        if (tags.isEmpty()&&author.isBlank()) {
            return recipeMapper.toRecipeDto(recipeRepository.findByNameContaining(keyword,pageable).getContent());
        }

        if (author.isBlank()) {
            return recipeMapper.toRecipeDto(recipeRepository.findByTagsAllAndNameContaining(keyword,tagsRef,pageable).getContent());
        }

        if (tags.isEmpty()) {
            return recipeMapper.toRecipeDto(recipeRepository.findByNameContainingAndAuthor(keyword,authorRef,pageable).getContent());
        }

        if (keyword.isBlank()) {
            return recipeMapper.toRecipeDto(recipeRepository.findByTagsAndAuthor(tagsRef,authorRef,pageable).getContent());
        }


        return recipeMapper.toRecipeDto(recipeRepository.findByTagsAllAndNameContainingAndAuthor(keyword, tagsRef, authorRef, pageable).getContent());


    }
}
