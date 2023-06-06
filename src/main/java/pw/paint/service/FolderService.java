package pw.paint.service;

import pw.paint.DTOs.model.ShortRecipeDto;

import java.util.List;

public interface FolderService {

    List<String> getFoldersNames(String username);

    List<ShortRecipeDto> getRecipesFromFolder(String username, String folderName);

    String createNewFolder(String userName, String folderName);

    String addRecipeToFolder(String username, String folderName, String recipeId);
}
