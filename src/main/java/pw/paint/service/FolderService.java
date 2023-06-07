package pw.paint.service;

import org.bson.types.ObjectId;
import pw.paint.DTOs.model.ShortRecipeDto;

import java.util.List;

public interface FolderService {

    List<String> getFoldersNames(String username);

    List<ShortRecipeDto> getRecipesFromFolder(String username, String folderName);

    void createNewFolder(String userName, String folderName);

    ObjectId addRecipeToFolder(String username, String folderName, String recipeId);
}
