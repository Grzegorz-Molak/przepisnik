package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.RecipeDto;
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
}
