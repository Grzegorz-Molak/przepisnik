package pw.paint.controller;

import lombok.RequiredArgsConstructor;
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

}
