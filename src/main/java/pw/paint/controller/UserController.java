package pw.paint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.paint.DTOs.model.UserDto;
import pw.paint.model.User;
import pw.paint.repository.UserRepository;
import pw.paint.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    @Autowired
    public  UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public String signUp(UserDto userDto){
        userService.signup(userDto);
        return "user added successfully";
    }


    //strefa testów do usnięcia na koniec

   @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
