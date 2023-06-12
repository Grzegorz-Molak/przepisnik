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
import org.springframework.web.multipart.MultipartFile;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.DTOs.requests.NewRecipeRequest;
import pw.paint.DTOs.requests.SearchRequest;
import pw.paint.exception.RecipeNotFoundException;
import pw.paint.exception.UserNotFoundException;
import pw.paint.service.JwtService;
import pw.paint.service.RecipeService;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final JwtService jwtService;

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        return ResponseEntity.ok(recipeService.getAllTags());
    }

//    @PostMapping("/new")
//    public ResponseEntity<Void> createNewRecipe(@RequestParam(value = "image", required = false) MultipartFile image, @ModelAttribute NewRecipeRequest newRecipeRequest) {
//        try {
//            byte[] imageBytes = (image != null) ? image.getBytes() : null;
//            return ResponseEntity.created(new URI("/recipe/" +
//                    recipeService.createNewRecipe(newRecipeRequest, imageBytes))).build();
//        } catch (UserNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .header("Error-message", ex.getMessage())
//                    .build();
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .header("Error-message", ex.getMessage())
//                    .build();
//        }
//    }

//    @PostMapping("/new")
//    public ResponseEntity<Void> createNewRecipe(@ModelAttribute NewRecipeRequest newRecipeRequest) {
//        try {
//            return ResponseEntity.created(new URI("/recipe/" +
//                    recipeService.createNewRecipe(newRecipeRequest))).build();
//        } catch (UserNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .header("Error-message", ex.getMessage())
//                    .build();
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .header("Error-message", ex.getMessage())
//                    .build();
//        }
//    }

    @PostMapping("/new")
    public ResponseEntity<String> createNewRecipe(@RequestBody NewRecipeRequest newRecipeRequest,
                                                @CookieValue(name = "token", required = false) String token ) {
        try {
            if(token == null || !newRecipeRequest.getAuthor().equals(jwtService.extractUsername(token))){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(recipeService.createNewRecipe(newRecipeRequest).toString());
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


    @PutMapping("/set-img/{id}")
    public ResponseEntity<Void> setImage(@PathVariable String id, @ModelAttribute MultipartFile image,
                                         @CookieValue(name = "token", required = false) String token ) {
        try {
            if(token == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.created(new URI("/recipe/" +
                    recipeService.setImage(id, image))).build();
        } catch (RecipeNotFoundException ex) {
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
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable String id,
                                                   @CookieValue(name = "token", required = false) String token ) {
        try {
            var recipe = recipeService.getRecipeById(new ObjectId(id));
            if(!recipe.getStatus()){
                if(token == null || !Objects.equals(recipe.getAuthor(), jwtService.extractUsername(token))){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .header("Error-message", "Forbidden")
                            .build();
                }
            }
            return ResponseEntity.ok(recipe);
        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable String id,
                                             @CookieValue(name = "token", required = false) String token ) {
        try {
            if(token == null ||
                    !recipeService.getRecipeById(new ObjectId(id)).getAuthor().equals(jwtService.extractUsername(token))){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            recipeService.changeStatus(id);
            return ResponseEntity.ok().build();
        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<ShortRecipeDto>> search(@RequestBody SearchRequest searchRequest){
        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return ResponseEntity.ok(recipeService.search(searchRequest.getAuthor(),
                searchRequest.getKeyword(), searchRequest.getTags(), true,pageable));
    }

    @PostMapping("/search/private")
    public ResponseEntity<List<ShortRecipeDto>> searchPrivate(@RequestBody SearchRequest searchRequest,
                                                              @CookieValue(name = "token", required = false) String token ){

        //TO DO sprawdzanie uprawnień czy podany autor w request body to user z tokenu
        if(token == null || !searchRequest.getAuthor().equals(jwtService.extractUsername(token))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("Error-message", "Forbidden")
                    .build();
        }

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return ResponseEntity.ok(recipeService.search(searchRequest.getAuthor(),
                searchRequest.getKeyword(), searchRequest.getTags(), false,pageable));
    }

    @DeleteMapping("{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String recipeId,
                                             @CookieValue(name = "token", required = false) String token ){
        try {
            if(token == null ||
                    !recipeService.getRecipeById(new ObjectId(recipeId)).getAuthor()
                    .equals(jwtService.extractUsername(token))){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            recipeService.deleteRecipe(new ObjectId(recipeId));
            return ResponseEntity.ok().build();
        } catch (RecipeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }



}
