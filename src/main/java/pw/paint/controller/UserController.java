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

    //stąd ten kontroler ma zniknąć potem
    private UserService userService;
    private final UserRepository userRepository;
    @Autowired
    public  UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String signUp(UserDto userDto){
        userService.signup(userDto);
        return "user added successfully";
    }


    //strefa testów do usnięcia na koniec

   @GetMapping
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

}
