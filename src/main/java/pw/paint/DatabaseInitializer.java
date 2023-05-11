package pw.paint;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pw.paint.model.Ingredient;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.model.User;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
//@Profile("development")
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public DatabaseInitializer(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        // Tworzenie początkowych obiektów
        System.out.println("elo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\nelo\n");
        userRepository.deleteAll();
        recipeRepository.deleteAll();

        User user1 = new User(0, "Filip", "abcde", "emailFilip@pw.edu.pl");
        User user2 = new User(1, "Piotr", "12345", "emailPiotr@pw.edu.pl");
        userRepository.save(user1);
        userRepository.save(user2);

        Recipe recipe1 = new Recipe();
        recipe1.setName("Tosty");
        recipe1.setId(1);
        recipe1.setLikes(10);
//        recipe1.setAuthor(user1);

//        Map<Ingredient, String> ingredients = new HashMap<>();
//        ingredients.put(new Ingredient("chleb tostowy"), "dwie kromki");
//        ingredients.put(new Ingredient("ser"), "2 plasterki");
//        recipe1.setIngredients(ingredients);

        List<String> steps = new ArrayList<>();
        steps.add("Włóż ser między kromki");
        steps.add("Włóż tost to opiekacza");
        steps.add("Czekaj 3 minuty");
        steps.add("Wyjmi. Smacznego!");
        recipe1.setSteps(steps);

//        List<Tag> tags = new ArrayList<>();
//        recipe1.getTags().add(new Tag("łatwe"));
//        recipe1.getTags().add(new Tag("śniadanie"));
//        recipe1.setTags();

        recipe1.setStatus(true);
        recipe1.setTimeMinutes(5);
        recipeRepository.save(recipe1);
    }
}

