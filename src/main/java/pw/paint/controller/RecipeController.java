package pw.paint.controller;


import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/search")
    public ResponseEntity<List<ShortRecipeDto>> search(@RequestBody SearchRequest searchRequest){
        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return ResponseEntity.ok(recipeService.search(searchRequest.getAuthor(),
                searchRequest.getKeyword(), searchRequest.getTags(), true,pageable));
    }

    @PostMapping("/search/private")
    public ResponseEntity<List<ShortRecipeDto>> searchPrivate(@RequestBody SearchRequest searchRequest){

        //TO DO sprawdzanie uprawnień czy podany user to user z tokenu

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return ResponseEntity.ok(recipeService.search(searchRequest.getAuthor(),
                searchRequest.getKeyword(), searchRequest.getTags(), false,pageable));
    }
}
