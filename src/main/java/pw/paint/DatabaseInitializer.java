package pw.paint;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import pw.paint.model.*;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public DatabaseInitializer(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception{


        User user = new User( "Piotr", "tajnehaslo", "kuchniaPiotra@gmail.com");
        userRepository.save(user);

        List<Folder> folders = new ArrayList<>();
        Folder folder = new Folder();
        folder.setName("moje autorksie przepisy");
        folders.add(folder);
        userRepository.save(user);

        Optional<User> userWithId = userRepository.findByUsername("Piotr");

        Recipe recipe1 = new Recipe();
        recipe1.setName("Tosty");
        recipe1.setLikes(10);
        if (userWithId.isPresent()) {
            recipe1.setAuthor(userWithId.get());
        }

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("chleb tostowy", "dwie kromki"));
        ingredients.add(new Ingredient("ser", "2 plasterki"));
        recipe1.setIngredients(ingredients);

        List<String> steps = new ArrayList<>();
        steps.add("Włóż ser między kromki");
        steps.add("Włóż tost to opiekacza");
        steps.add("Czekaj 3 minuty");
        steps.add("Wyjmi. Smacznego!");
        recipe1.setSteps(steps);

        List<String> tags = new ArrayList<>();
        tags.add("łatwe");
        tags.add("śniadanie");
        recipe1.setTags(tags);

        recipe1.setStatus(true);
        recipe1.setTimeMinutes(5);
        recipeRepository.save(recipe1);

        List <Folder> folders2 = userWithId.get().getFolders();

        for (Folder folder2 : folders2){
            if(folder2.getName().equals("moje autorksie przepisy")){
                folder2.getRecipes().add(recipe1);
            }
        }
        userRepository.save(user);
    }



}

