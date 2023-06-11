package pw.paint.controller;


import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.DTOs.requests.NewRecipeRequest;
import pw.paint.DTOs.requests.SearchRequest;
import pw.paint.exception.RecipeNotFoundException;
import pw.paint.exception.UserNotFoundException;
import pw.paint.service.RecipeService;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        return ResponseEntity.ok(recipeService.getAllTags());
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createNewRecipe(@RequestBody NewRecipeRequest newRecipeRequest) {
        try {
            return ResponseEntity.created(new URI("/recipe/" +
                    recipeService.createNewRecipe(newRecipeRequest))).build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-message", ex.getMessage())
                    .build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-message", ex.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeById(new ObjectId(id)));
        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        try {
            byte[] image = recipeService.getImage(new ObjectId(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.length);

            return new ResponseEntity<>(image, headers, 200);

        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable String id) {
        try {
            recipeService.changeStatus(id);
            return ResponseEntity.ok().build();
        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

//    @GetMapping("/image/{recipeId}")
//    public ResponseEntity<byte[]> getImage(@PathVariable String recipeId) {
//        try {
//            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\recipejpg\\" + recipeId + ".jpg.";
//            Path filePath = Paths.get(imagePath);
//            byte[] imageBytes = Files.readAllBytes(filePath);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            headers.setContentLength(imageBytes.length);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(imageBytes);
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .header("Error-Message", ex.getMessage())
//                    .build();
//        }
//    }

    @PostMapping("/search")
    public ResponseEntity<List<ShortRecipeDto>> search(@RequestBody SearchRequest searchRequest){
        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return ResponseEntity.ok(recipeService.search(searchRequest.getAuthor(),
                searchRequest.getKeyword(), searchRequest.getTags(), true,pageable));
    }

    @PostMapping("/search/private")
    public ResponseEntity<List<ShortRecipeDto>> searchPrivate(@RequestBody SearchRequest searchRequest){

        //TO DO sprawdzanie uprawnień czy podany autor w request body to user z tokenu

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return ResponseEntity.ok(recipeService.search(searchRequest.getAuthor(),
                searchRequest.getKeyword(), searchRequest.getTags(), false,pageable));
    }

    @DeleteMapping("{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String recipeId){
        try {
            recipeService.deleteRecipe(new ObjectId(recipeId));
            return ResponseEntity.ok().build();
        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }



}
