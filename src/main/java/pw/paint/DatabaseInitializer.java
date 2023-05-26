package pw.paint;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pw.paint.model.*;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.TagRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private  final TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {

        tagRepository.save(new Tag("wegańskie"));
        tagRepository.save(new Tag("z mięsem"));
        tagRepository.save(new Tag("ostre"));
        tagRepository.save(new Tag("obiad"));
        tagRepository.save(new Tag("kolacja"));
        tagRepository.save(new Tag("śniadanie"));
        tagRepository.save(new Tag("kuchnia chińska"));
        tagRepository.save(new Tag("kuchnia włoska"));
        tagRepository.save(new Tag("kuchnia polska"));

        Optional<User> checkUser = userRepository.findByUsername("Piotr");
        if (!checkUser.isPresent()) {
            User user = new User("Piotr", "tajnehaslo", "kuchniaPiotra@gmail.com");
            userRepository.save(user);
            Optional<User> userWithId = userRepository.findByUsername("Piotr");
            List<Folder> folders = new ArrayList<>();
            Folder folder = new Folder();
            folder.setName("moje autorksie przepisy");
            folders.add(folder);
            userWithId.get().setFolders(folders);
            userRepository.save(userWithId.get());


            Recipe recipe1 = new Recipe();
            recipe1.setName("Szybka zapiekanka z mięsem mielonym i ziemniakami");
            recipe1.setLikes(10);
            if (userWithId.isPresent()) {
                recipe1.setAuthor(userWithId.get());
            }

            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient("mielona wołowina", "500 g"));
            ingredients.add(new Ingredient("cebula", "1 sztuka"));
            ingredients.add(new Ingredient("ziemniaki", "2 kg"));
            ingredients.add(new Ingredient("ser żółty", "220 g"));
            ingredients.add(new Ingredient("ser cheddar", "100 g"));
            ingredients.add(new Ingredient("bulion", "100 ml"));
            ingredients.add(new Ingredient("jajko", "1 sztuka"));
            ingredients.add(new Ingredient("natka pietruszki", "4 gałązki"));
            ingredients.add(new Ingredient("sól", "1/2 łyżeczki"));
            ingredients.add(new Ingredient("pieprz", "1/4 łyżeczki"));
            ingredients.add(new Ingredient("oliwa", "3 łyżki"));
            recipe1.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Cebulę obierz i drobno posiekaj.");
            steps.add("Na patelni rozgrzej oliwę. Dodaj wołowinę i cebulę. Smaż wszystko przez kilka minut do zrumienienia. Dopraw solą i pieprzem. W razie potrzeby odcedź tłuszcz.");
            steps.add("Duże naczynie żaroodporne nasmaruj oliwą. Na dnie połóż ok. 1/5 ziemniaków, a na to ok. ¼ wołowiny. Posyp odrobiną tartego sera żółtego. Powtarzaj te warstwy do wykończenia składników.");
            steps.add("W garnuszku wymieszaj bulion z mlekiem. Dodaj tarty ser cheddar i podgrzewaj wszystko, stale mieszając, do rozpuszczenia sera. Zdejmij z ognia i ostudź. Dodaj jajko i wymieszaj. Mieszanką zalej zapiekankę.");
            steps.add("Na wierzchu poukładaj odłożone wcześniej ziemniaki, by lekko na siebie zachodziły. Posyp siekaną natką pietruszki.");
            steps.add("Piekarnik nagrzej do 180 st. Celsjusza. Naczynie przykryj folią aluminiową. Szybką zapiekankę z mięsem mielonym i ziemniakami piecz przez 60 minut, do miękkości ziemniaków. Usuń folię i kontynuuj pieczenie przez kolejne 20 minut, do zrumienienia.");
            recipe1.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad");
            tags.add(tag);
            tag = tagRepository.findByName("z mięsem");
            tags.add(tag);
            recipe1.setTags(tags);

            recipe1.setStatus(true);
            recipe1.setTimeMinutes(5);
            recipeRepository.save(recipe1);

            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorksie przepisy")) {
                    if (folder2.getRecipes() == null) {
                        folder2.setRecipes(new ArrayList<Recipe>());
                        folder2.getRecipes().add(recipe1);

                    } else
                        folder2.getRecipes().add(recipe1);
                }
            }
            userRepository.save(userWithId.get());
        }

    }

}

