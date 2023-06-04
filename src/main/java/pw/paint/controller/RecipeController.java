package pw.paint.controller;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.requests.SearchRequest;
import pw.paint.service.RecipeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/tags")
    public List<String> getAllTags(){
        return recipeService.getAllTags();
    }

    @PostMapping("/new")
    public String createNewRecipe(@RequestBody RecipeDto recipeDto){
        return recipeService.createNewRecipe(recipeDto);
    }

    @PostMapping("/search")
    public List<RecipeDto> search(@RequestBody SearchRequest searchRequest){

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return recipeService.search(searchRequest.getAuthor(), searchRequest.getKeyword(),searchRequest.getTags(),pageable);

    }

}
