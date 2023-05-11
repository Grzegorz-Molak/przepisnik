package pw.paint.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.paint.model.Recipe;
import pw.paint.repository.RecipeRepository;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public List<Recipe> retrieveAllUsers() {
        return recipeRepository.findAll();
    }
}
