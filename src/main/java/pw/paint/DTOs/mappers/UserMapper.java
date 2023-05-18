package pw.paint.DTOs.mappers;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.UserDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
   public static UserDto toUserDto (User user){
       UserDto userDto = new UserDto();
       if (user.getFolders() != null) {
           List<FolderDto> folderDtos = new ArrayList<>();
           for (Folder folder : user.getFolders()) {
               FolderDto folderDto = new FolderDto();
               folderDto.setName(folder.getName());
               if(folder.getRecipes() != null) {
                   List<ObjectId> recipeIds = new ArrayList<>();
                   for (Recipe recipe : folder.getRecipes()) {
                       recipeIds.add(recipe.getId());
                   }
                   folderDto.setRecipes(recipeIds);
               }
               folderDtos.add(folderDto);
               userDto.setFolders(folderDtos);
           }
       }

       userDto.setId(user.getId());
       userDto.setUserName(user.getUsername());
       userDto.setPassword(user.getPassword());
       userDto.setEmail(user.getEmail());
       return userDto;
    }
}
