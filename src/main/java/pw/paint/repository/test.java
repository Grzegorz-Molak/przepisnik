/* package pw.paint.repository;


import com.mongodb.DBRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class test implements CommandLineRunner {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;



    public static void main(String[] args) {

        SpringApplication.run(test.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

        int pageNumber = 0; // Page number (0-based)
        int pageSize = 5; // Number of items per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        String keyword = "zie";
        Page<Recipe> recipePage = recipeRepository.findByNameContaining(keyword,pageable);
        int totalPages = recipePage.getTotalPages();
        for (Recipe recipe : recipePage) {
            System.out.println(recipe.getName());
        }

        List<DBRef> tags = new ArrayList<>();
        Optional<Tag> tag = tagRepository.findByName("wegetariańskie");
        if (tag.isPresent()) {
            tags.add(new DBRef("tags", tag.get().getId()));

        }

        Page<Recipe> recipePage2 = recipeRepository.findByTagsAll(tags,pageable);

        for (Recipe recipe : recipePage2) {
            System.out.println(recipe.getName());
        }

        Page<Recipe> recipePage3 = recipeRepository.findByTagsAllAndNameContaining(keyword,tags,pageable);

        for (Recipe recipe : recipePage3) {
            System.out.println(recipe.getName());
        }

        Optional<User> userWithId = userRepository.findByUsername("Agnieszka");
        if (userWithId.isPresent()) {
            List<Recipe> recipesA = recipeRepository.findByAuthor(userWithId.get());

            for (Recipe recipe : recipesA) {
                System.out.println(recipe.getName());
            }
        }

        userWithId = userRepository.findByUsername("Ania");
        DBRef author = new DBRef("users",userWithId.get().getId());
        Page<Recipe> recipePage4 = recipeRepository.findByTagsAllAndNameContainingAndAuthor(keyword,tags,author,pageable);

        for (Recipe recipe : recipePage4) {
            System.out.println(recipe.getName());
        }


    }
}

*/

