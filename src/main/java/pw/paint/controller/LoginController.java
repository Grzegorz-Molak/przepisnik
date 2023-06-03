package pw.paint.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("dodaj-przepis.html");
    }

    @PostMapping("/login")
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
}
