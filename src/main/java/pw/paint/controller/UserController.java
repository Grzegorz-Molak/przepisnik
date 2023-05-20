package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public String signUp(SignUpRequest signUpRequest){
        userService.signup(signUpRequest);
        return "user added successfully";
    }



    //strefa testów do usunięcia na koniec
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
