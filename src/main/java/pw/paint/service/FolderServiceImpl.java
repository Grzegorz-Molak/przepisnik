package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.FolderMapper;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.User;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public List<String> getFoldersNames(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent())
            return null;
        if (user.get().getFolders().isEmpty()) {
            return null;
        }
        List<String> foldersNames = new ArrayList<>();
        for(Folder folder :user.get().getFolders()) {
            foldersNames.add(folder.getName());
        }
        return foldersNames;
    }

    @Override
    public List<ShortRecipeDto> getRecipesFromFolder(String username, String folderName) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return null;
        }

        if (user.get().getFolders().isEmpty()) {
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();
        for (Folder folder : user.get().getFolders()) {
            if (folder.getName().equals(folderName)) {
                if (folder.getRecipes() == null) {
                    return null;
                }
                recipes = folder.getRecipes();
                return recipeMapper.toShortRecipeDto(recipes);
            }
        }
        return null;
    }

    @Override
    public String createNewFolder(String username, String folderName) {

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return "Nie ma takiego użytkownika";
        }

        Folder folder = user.get().findFolderByName(folderName);
        if (folder != null) {
            return "Folder o takiej nazwie już istnieje";
        }

        user.get().getFolders().add(new Folder(folderName));

        userRepository.save(user.get());


        return "Utworzono nowy folder";
    }

    @Override
    public String addRecipeToFolder(String username, String folderName, String recipeId) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return "Nie ma takiego użytkownika";
        }

        Folder folder = user.get().findFolderByName(folderName);

        if (folder == null) {
            return "Użytkownik nie ma takiego folderu";
        }

        ObjectId id = new ObjectId(recipeId);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(!recipe.isPresent()){
            return "Nie ma takiego przepisu";
        }
        if(folder.getRecipes() == null){
            folder.setRecipes(new ArrayList<>());
            folder.getRecipes().add(recipe.get());
        }
        else {
            folder.getRecipes().add(recipe.get());
        }

        userRepository.save(user.get());
        return "Dodano przepis do folderu";


    }
}
