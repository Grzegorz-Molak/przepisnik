package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.service.FolderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    @GetMapping("/{username}")
    public List<String> getUserFolders(@PathVariable String username) {
        return folderService.getFoldersNames(username);
    }
    @GetMapping("/{username}/{folderName}")
    public List<ShortRecipeDto> getRecipesFromFolder(@PathVariable String username,@PathVariable String folderName){
        return folderService.getRecipesFromFolder(username, folderName);
    }

    @PostMapping("/new/{username}/{folderName}")
    public String createNewFolder(@PathVariable String username, @PathVariable String folderName){
        return folderService.createNewFolder(username,folderName);
    }

    @PutMapping("/add/{username}/{folderName}/{recipeId}")
    public String addRecipeToFolder(@PathVariable String username, @PathVariable String folderName,@PathVariable String recipeId) {
        return folderService.addRecipeToFolder(username,folderName,recipeId);
    }
}
