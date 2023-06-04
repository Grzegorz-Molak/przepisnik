package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.*;
import pw.paint.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    //Do wywalenia bo już reg i log jest w AuthenticationController
    @PostMapping()
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signup(signUpRequest);
        return "user added successfully";
    }

    @GetMapping("/recipes")
    public List<RecipeDto> getUserRecipes(@RequestBody String username) {
        return userService.getUserRecipes(username);
    }

    @GetMapping("/folders")
    public List<FolderDto> getUserFolders(@RequestBody String username) {
        return userService.getFolders(username);
    }

    @GetMapping("/folders/")
    public List<RecipeDto> getFolderRecipes(@RequestBody UserDto userDto, @RequestParam("name") String name) {
        return userService.getFolderRecipes(userDto, name);
    }

    @GetMapping("/id")
    public UserDto getUser(@RequestBody String idStr) {
        ObjectId id = new ObjectId(idStr);
        return userService.getUserById(id);
    }

    @PutMapping("/folder-add")
    public String addToFolder(@RequestBody RecipeDto recipeDto, @RequestParam String name) {
        return userService.addToFolder(recipeDto, name);
    }


    //strefa testów do usunięcia na koniec
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

//    @PostMapping("/newFolder/{userName}/{folderName}")
//    public String createNewFolder(@PathVariable String userName, @PathVariable String folderName){
//        return userService.createNewFolder(userName,folderName);
//    }

    @PostMapping("/newFolder")
    public String createNewFolder(@RequestBody String userName, @RequestBody String folderName){
        return userService.createNewFolder(userName,folderName);
    }

}
