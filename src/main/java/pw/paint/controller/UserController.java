package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.*;
import pw.paint.exception.UserNotFoundException;
import pw.paint.service.JwtService;
import pw.paint.service.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.getUserById(new ObjectId(id)));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    //strefa testów do usunięcia na koniec
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }


//    @GetMapping("/image")
//    public ResponseEntity<byte[]> getImage() {
//        try {
//            String imagePath = "..\\przepisnik\\test.jpg";
//            Path path = Paths.get(imagePath);
//            byte[] imageBytes = Files.readAllBytes(path);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            headers.setContentLength(imageBytes.length);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(imageBytes);
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    private final JwtService jwtService;
    @GetMapping("/test/{token}")
    public String getME(@PathVariable String token) {
        return jwtService.extractUsername(token);
    }
}
