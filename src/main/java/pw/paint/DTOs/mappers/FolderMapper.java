package pw.paint.DTOs.mappers;

import org.bson.types.ObjectId;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FolderMapper {
    public static FolderDto toFolderDto (Folder folder) {
        FolderDto folderDto = new FolderDto();
        folderDto.setName(folder.getName());
        if(folder.getRecipes() != null) {
            List<ObjectId> recipeIds = new ArrayList<>();
            for (Recipe recipe : folder.getRecipes()) {
                recipeIds.add(recipe.getId());
            }
            folderDto.setRecipes(recipeIds);
        }
        return folderDto;
    }
}
