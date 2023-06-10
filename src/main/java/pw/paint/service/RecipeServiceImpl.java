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
import pw.paint.exception.ImageProcessingException;
import pw.paint.exception.RecipeNotFoundException;
import pw.paint.exception.UserNotFoundException;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.model.User;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.TagRepository;
import pw.paint.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
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
        List<Tag> tags = tagRepository.findAll();

        List<String> tags_s = new ArrayList<>();

        for (Tag tag : tags) {
            tags_s.add(tag.getName());
        }
        return tags_s;
    }

    @Override
    public ObjectId createNewRecipe(NewRecipeRequest newRecipeRequest) {
        Optional<User> author = userRepository.findByUsername(newRecipeRequest.getAuthor());
        if (author.isEmpty())
            throw new UserNotFoundException("User not found; The recipe must have an author");

        Folder defaultFolder = author.get().findFolderByName(defaultFolderName);

        //Podobno mogą być takie same ustaliliśmy
        /*List<Recipe> recipes = defaultFolder.getRecipes();
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(newRecipeRequest.getName())) {
                return "Przepis o takiej nazwie już istnieje";
            }
        }*/

        Recipe recipe = recipeMapper.toModelRecipeObject(newRecipeRequest);

        if (defaultFolder == null) {
            Folder folder = new Folder(defaultFolderName);
            folder.setRecipes(new ArrayList<>());
            folder.getRecipes().add(recipe);
            author.get().getFolders().add(folder);
        } else if (defaultFolder.getRecipes() == null) {
            defaultFolder.setRecipes(new ArrayList<>());
            defaultFolder.getRecipes().add(recipe);
        } else {
            defaultFolder.getRecipes().add(recipe);
        }

        recipeRepository.save(recipe);
        userRepository.save(author.get());

        try {
//            String imagePath = "..\\przepisnik\\test.jpg";
//            Path path = Paths.get(imagePath);
//            byte[] imageData = Files.readAllBytes(path);

            byte[] imageData = newRecipeRequest.getImage();

            if (imageData != null && imageData.length > 0) {
                String fileName = recipe.getId() + ".jpg";
                String directory = "..\\przepisnik\\src\\main\\resources\\static\\recipejpg";
                Path filePath = Paths.get(directory, fileName);
                Files.write(filePath, imageData);
            }

        } catch (Exception ex) {
            throw new ImageProcessingException();
        }

        return recipe.getId();
    }


    @Override
    public List<ShortRecipeDto> search(String author, String keyword, List<String> tags, Boolean status, Pageable pageable) {

        List<ShortRecipeDto> recipes = new ArrayList<>();

        if (keyword.isBlank() && tags.isEmpty() && author.isBlank()) {
            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findAll(true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findAll(false, pageable).getContent()));
            }

            return recipes;
        }

        List<DBRef> tagsRef = new ArrayList<>();
        Optional<Tag> tag;
        for (String tagName : tags) {
            tag = tagRepository.findByName(tagName);
            tag.ifPresent(value -> tagsRef.add(new DBRef("tags", value.getId())));
        }

        Optional<User> userWithId = userRepository.findByUsername(author);
        DBRef authorRef = null;
        if (userWithId.isPresent()) {
            authorRef = new DBRef("users", userWithId.get().getId());
        }

        if (keyword.isBlank() && tags.isEmpty()) {
            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByAuthor(authorRef, true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByAuthor(authorRef, false, pageable).getContent()));
            }
            return recipes;
        }

        if (keyword.isBlank() && author.isBlank()) {

            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAll(tagsRef, true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAll(tagsRef, false, pageable).getContent()));
            }
            return recipes;
        }

        if (tags.isEmpty() && author.isBlank()) {
            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByNameContaining(keyword, true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByNameContaining(keyword, false, pageable).getContent()));
            }
            return recipes;

        }

        if (author.isBlank()) {

            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAllAndNameContaining(keyword, tagsRef, true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAllAndNameContaining(keyword, tagsRef, false, pageable).getContent()));
            }
            return recipes;
        }

        if (tags.isEmpty()) {

            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByNameContainingAndAuthor(keyword, authorRef, true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByNameContainingAndAuthor(keyword, authorRef, false, pageable).getContent()));
            }
            return recipes;
        }

        if (keyword.isBlank()) {

            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAndAuthor(tagsRef, authorRef, true, pageable).getContent()));
            if (status == false) {
                recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAndAuthor(tagsRef, authorRef, false, pageable).getContent()));
            }
            return recipes;

        }


        recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAllAndNameContainingAndAuthor(keyword, tagsRef, authorRef, true, pageable).getContent()));
        if (status == false) {
            recipes.addAll(RecipeMapper.toShortRecipeDto(recipeRepository.findByTagsAllAndNameContainingAndAuthor(keyword, tagsRef, authorRef, false, pageable).getContent()));
        }
        return recipes;

    }

    @Override
    public RecipeDto getRecipeById(ObjectId id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.map(RecipeMapper::toRecipeDto).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public void deleteRecipe(ObjectId id) {
        List<User> users = userRepository.findAll();

        if (users == null) throw new UserNotFoundException();

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) throw new RecipeNotFoundException();


        for (User user : users) {
            if (user.getFolders() != null) {
                Iterator<Folder> folderIterator = user.getFolders().iterator();
                while (folderIterator.hasNext()) {
                    Folder folder = folderIterator.next();
                    if (folder.getRecipes() != null) {
                        Iterator<Recipe> recipeIterator = folder.getRecipes().iterator();
                        while (recipeIterator.hasNext()) {
                            Recipe recipeInFolder = recipeIterator.next();
                            if (recipeInFolder.getId().equals(id)) {
                                recipeIterator.remove();
                            }
                        }
                    }
                }
            }
            userRepository.save(user);
        }
        recipeRepository.delete(recipe.get());
    }

    @Override
    public void changeStatus(String id) {
        Recipe recipe = recipeRepository.findById(new ObjectId(id)).orElse(null);
        if (recipe == null)
            throw new RecipeNotFoundException();
        if(!recipe.getStatus()){
            recipe.setStatus(true);
        }
        recipeRepository.save(recipe);
    }
}
