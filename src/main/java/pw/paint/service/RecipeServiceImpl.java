package pw.paint.service;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.DTOs.requests.NewRecipeRequest;
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
    private final String defaultFolderName = "moje autorskie przepisy";

   // @Override
    //public List<RecipeDto> getAllRecipes() {

      //  return recipeMapper.toRecipeDto(recipeRepository.findAll());

    //}

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
    public String createNewRecipe(NewRecipeRequest newRecipeRequest) {
        Optional<User> author = userRepository.findByUsername(newRecipeRequest.getAuthor());

        if (!author.isPresent()) {
            return "Nie ma takiego użytkownika. Przepis musi mieć autora";
        }

        Folder defaultFolder = author.get().findFolderByName(defaultFolderName);

// Podobno mogą być takie same ustaliliśmy
//        List<Recipe> recipes = defaultFolder.getRecipes();
//        for (Recipe recipe : recipes) {
//            if (recipe.getName().equals(newRecipeRequest.getName())) {
//                return "Przepis o takiej nazwie już istnieje";
//            }
//        }


        Recipe recipe = recipeMapper.toModelRecipeObject(newRecipeRequest);

        if (defaultFolder == null ){
            Folder folder = new Folder(defaultFolderName);
            folder.setRecipes(new ArrayList<>());
            folder.getRecipes().add(recipe);
            author.get().getFolders().add(folder);
        }
        else if(defaultFolder.getRecipes() == null){
            defaultFolder.setRecipes(new ArrayList<>());
            defaultFolder.getRecipes().add(recipe);
        }
        else{
            defaultFolder.getRecipes().add(recipe);
        }


        recipeRepository.save(recipe);
        userRepository.save(author.get());
        return "Dodano nowy przepis";
    }

    @Override
    public List<ShortRecipeDto> search(String author, String keyword, List<String> tags, Pageable pageable) {

        if (keyword.isBlank()&&tags.isEmpty()&&author.isBlank()) {
            return recipeMapper.toShortRecipeDto(recipeRepository.findAll(pageable).getContent());
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
            return recipeMapper.toShortRecipeDto(recipeRepository.findByAuthor(authorRef, pageable).getContent());
        }

        if (keyword.isBlank()&&author.isBlank()) {
            return recipeMapper.toShortRecipeDto(recipeRepository.findByTagsAll(tagsRef, pageable).getContent());
        }

        if (tags.isEmpty()&&author.isBlank()) {
            return recipeMapper.toShortRecipeDto(recipeRepository.findByNameContaining(keyword,pageable).getContent());
        }

        if (author.isBlank()) {
            return recipeMapper.toShortRecipeDto(recipeRepository.findByTagsAllAndNameContaining(keyword,tagsRef,pageable).getContent());
        }

        if (tags.isEmpty()) {
            return recipeMapper.toShortRecipeDto(recipeRepository.findByNameContainingAndAuthor(keyword,authorRef,pageable).getContent());
        }

        if (keyword.isBlank()) {
            return recipeMapper.toShortRecipeDto(recipeRepository.findByTagsAndAuthor(tagsRef,authorRef,pageable).getContent());
        }


        return recipeMapper.toShortRecipeDto(recipeRepository.findByTagsAllAndNameContainingAndAuthor(keyword, tagsRef, authorRef, pageable).getContent());


    }

    @Override
    public RecipeDto getRecipeById(ObjectId id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()){
            return recipeMapper.toRecipeDto(recipe.get());
        }
        else{
            return null;
        }
    }
}
