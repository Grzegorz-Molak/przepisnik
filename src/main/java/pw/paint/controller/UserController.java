package pw.paint.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.SignUpRequest;
import pw.paint.DTOs.model.UserDto;
import pw.paint.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public String signUp(@RequestBody SignUpRequest signUpRequest){
        userService.signup(signUpRequest);
        return "user added successfully";
    }



    //strefa testów do usunięcia na koniec
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/newFolder/{userName}/{folderName}")
    public String createNewFolder(@PathVariable String userName, @PathVariable String folderName){
        return userService.createNewFolder(userName,folderName);
    }

}
