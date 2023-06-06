package pw.paint.service;

import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.ShortRecipeDto;

import java.util.List;

public interface FolderService {

    List<FolderDto> getFolders(String username);

    List<ShortRecipeDto> getRecipesFromFolder(String username, String folderName);

    String createNewFolder(String userName, String folderName);

    String addRecipeToFolder(String username, String folderName, String recipeId);
}
