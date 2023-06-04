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
    private final TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {

        List<String> tags_s = new ArrayList<>();
        tags_s.add("wegetariańskie");
        tags_s.add("wegańskie");
        tags_s.add("łagodne");
        tags_s.add("pikantne");
        tags_s.add("ostre");
        tags_s.add("bez glutenu");
        tags_s.add("bez laktozy");
        tags_s.add("śniadanie");
        tags_s.add("obiad");
        tags_s.add("kolacja");
        tags_s.add("deser");
        tags_s.add("przekąska");
        tags_s.add("zupa");
        tags_s.add("włoskie");
        tags_s.add("azjatyckie");
        tags_s.add("polskie");
        tags_s.add("amerykańskie");
        tags_s.add("meksykańskie");

        Optional<Tag> tag_s;
        for (String tagName : tags_s) {
            tag_s = tagRepository.findByName(tagName);
            if (!tag_s.isPresent()) {
                tagRepository.save(new Tag(tagName));
            }
        }

        Optional<User> checkUser = userRepository.findByUsername("Ania");
        if (!checkUser.isPresent()) {
            User user = new User("Ania", "tajnehaslo", "ania.gotuje@gmail.com");
            userRepository.save(user);
            Optional<User> userWithId = userRepository.findByUsername("Ania");
            List<Folder> folders = new ArrayList<>();
            Folder folder = new Folder();
            folder.setName("moje autorksie przepisy");
            folders.add(folder);
            userWithId.get().setFolders(folders);
            userRepository.save(userWithId.get());
        }

        checkUser = userRepository.findByUsername("Agnieszka");
        if (!checkUser.isPresent()) {
            User user = new User("Agnieszka", "quackquack", "agnieszka.gotuje@gmail.com");
            userRepository.save(user);
            Optional<User> userWithId = userRepository.findByUsername("Agnieszka");
            List<Folder> folders = new ArrayList<>();
            Folder folder = new Folder();
            folder.setName("moje autorksie przepisy");
            folders.add(folder);
            userWithId.get().setFolders(folders);
            userRepository.save(userWithId.get());
        }

        Optional<Recipe> checkRecipe = recipeRepository.findByName("Szybka zapiekanka z mięsem mielonym i ziemniakami");
        if (!checkRecipe.isPresent()) {
            Recipe recipe1 = new Recipe();
            recipe1.setName("Szybka zapiekanka z mięsem mielonym i ziemniakami");
            recipe1.setLikes(10);


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
            steps.add("1. Cebulę obierz i drobno posiekaj.");
            steps.add("2. Na patelni rozgrzej oliwę. Dodaj wołowinę i cebulę. Smaż wszystko przez kilka minut do zrumienienia. Dopraw solą i pieprzem. W razie potrzeby odcedź tłuszcz.");
            steps.add("3. Duże naczynie żaroodporne nasmaruj oliwą. Na dnie połóż ok. 1/5 ziemniaków, a na to ok. ¼ wołowiny. Posyp odrobiną tartego sera żółtego. Powtarzaj te warstwy do wykończenia składników.");
            steps.add("4. W garnuszku wymieszaj bulion z mlekiem. Dodaj tarty ser cheddar i podgrzewaj wszystko, stale mieszając, do rozpuszczenia sera. Zdejmij z ognia i ostudź. Dodaj jajko i wymieszaj. Mieszanką zalej zapiekankę.");
            steps.add("5. Na wierzchu poukładaj odłożone wcześniej ziemniaki, by lekko na siebie zachodziły. Posyp siekaną natką pietruszki.");
            steps.add("6. Piekarnik nagrzej do 180 st. Celsjusza. Naczynie przykryj folią aluminiową. Szybką zapiekankę z mięsem mielonym i ziemniakami piecz przez 60 minut, do miękkości ziemniaków. Usuń folię i kontynuuj pieczenie przez kolejne 20 minut, do zrumienienia.");
            recipe1.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Optional<Tag> tag = tagRepository.findByName("obiad");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }


            Optional<User> userWithId = userRepository.findByUsername("Ania");
            if (userWithId.isPresent()) {
                recipe1.setAuthor(userWithId.get());
            }

            recipe1.setTags(tags);
            recipe1.setStatus(true);
            recipe1.setTimeMinutes(5);
            recipeRepository.save(recipe1);


            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorksie przepisy")) {
                    if (folder2.getRecipes() == null) {
                        folder2.setRecipes(new ArrayList<>());
                        folder2.getRecipes().add(recipe1);

                    } else
                        folder2.getRecipes().add(recipe1);
                }
            }
            userRepository.save(userWithId.get());
        }

        checkRecipe = recipeRepository.findByName("Placki z serkiem");
        if (!checkRecipe.isPresent()) {
            Recipe recipe2 = new Recipe();
            recipe2.setName("Placki z serkiem");
            recipe2.setLikes(0);

            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient("serek waniliowy", "400 g"));
            ingredients.add(new Ingredient("jajka", "2 sztuki"));
            ingredients.add(new Ingredient("mąka", "1 szklanka"));
            ingredients.add(new Ingredient("proszek do pieczenia", "1 łyżeczka"));
            ingredients.add(new Ingredient("olej", "2 łyżki"));
            recipe2.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("1. Ubić pianę z białek.");
            steps.add("2. Dodać cukier.");
            steps.add("3. Wymieszać z żółtkami i serkiem waniliowym.");
            steps.add("4. Dodać proszek do pieczenia.");
            steps.add("5. Smażyć na oleju.");
            recipe2.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Optional<Tag> tag = tagRepository.findByName("deser");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("wegetariańskie");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            Optional<User> userWithId = userRepository.findByUsername("Agnieszka");
            if (userWithId.isPresent()) {
                recipe2.setAuthor(userWithId.get());
            }

            recipe2.setTags(tags);
            recipe2.setStatus(true);
            recipe2.setTimeMinutes(5);
            recipeRepository.save(recipe2);

            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorksie przepisy")) {
                    if (folder2.getRecipes() == null) {
                        folder2.setRecipes(new ArrayList<>());
                        folder2.getRecipes().add(recipe2);

                    } else {
                        folder2.getRecipes().add(recipe2);
                    }
                }
            }
            userRepository.save(userWithId.get());

        }


        checkRecipe = recipeRepository.findByName("Placki ziemniaczane");
        if (!checkRecipe.isPresent()) {
            Recipe recipe3 = new Recipe();
            recipe3.setName("Placki ziemniaczane");
            recipe3.setLikes(0);

            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient("ziemniaki", "0.5 kg"));
            ingredients.add(new Ingredient("mąka pszenna", "0.5 łyżki"));
            ingredients.add(new Ingredient("cebula", "1/4 sztuki"));
            ingredients.add(new Ingredient("jajko", "1 sztuka"));
            ingredients.add(new Ingredient("sól", "2 szczypty"));
            ingredients.add(new Ingredient("olej roślinny", "2 łyżki"));
            recipe3.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("1. Rozetrzeć ziemniaki na tarce, zostawić na kilka minut, odlać zebrany sok.");
            steps.add("2. Do ziemniaków dodać mąkę, drobno startą cebulę, jajko i sól.");
            steps.add("3. Masę wymieszać i smażyć małe porcje ciasta na rozgrzanym oleju na złoty kolor.");
            recipe3.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Optional<Tag> tag = tagRepository.findByName("obiad");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("kuchnia polska");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("wegetariańskie");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            Optional<User> userWithId = userRepository.findByUsername("Agnieszka");
            if (userWithId.isPresent()) {
                recipe3.setAuthor(userWithId.get());
            }

            recipe3.setTags(tags);
            recipe3.setStatus(true);
            recipe3.setTimeMinutes(5);
            recipeRepository.save(recipe3);

            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorksie przepisy")) {
                    if (folder2.getRecipes() == null) {
                        folder2.setRecipes(new ArrayList<>());
                        folder2.getRecipes().add(recipe3);

                    } else {
                        folder2.getRecipes().add(recipe3);
                    }
                }
            }
            userRepository.save(userWithId.get());

        }

    }
}

