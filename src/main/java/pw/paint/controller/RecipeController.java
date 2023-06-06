package pw.paint.controller;


import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.DTOs.requests.NewRecipeRequest;
import pw.paint.DTOs.requests.SearchRequest;
import pw.paint.service.RecipeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

   // @GetMapping
   // public List<RecipeDto> getAllRecipes() {
     //   return recipeService.getAllRecipes();
   // }

    @GetMapping("/tags")
    public List<String> getAllTags(){
        return recipeService.getAllTags();
    }

    @PostMapping("/new")
    public String createNewRecipe(@RequestBody NewRecipeRequest newRecipeRequest){
        return recipeService.createNewRecipe(newRecipeRequest);
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipeById(@PathVariable String id){
        ObjectId objectId = new ObjectId(id);
        return recipeService.getRecipeById(objectId);
    }

    @PostMapping("/search")
    public List<ShortRecipeDto> search(@RequestBody SearchRequest searchRequest){

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(),searchRequest.getPageSize());
        return recipeService.search(searchRequest.getAuthor(), searchRequest.getKeyword(),searchRequest.getTags(),pageable);

    }

}
