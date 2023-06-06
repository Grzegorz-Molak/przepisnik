package pw.paint.service;

import org.bson.types.ObjectId;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();


    List<RecipeDto> getUserRecipes(String username);

    UserDto getUserById(ObjectId id);



    //List<RecipeDto> getFolderRecipes(UserDto userDto, String name);

    String addToFolder(RecipeDto recipeDto, String folderName);
}
