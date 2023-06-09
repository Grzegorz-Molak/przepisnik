package pw.paint;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            Optional<User> userWithId = userRepository.findByUsername("Ania");
            List<Folder> folders = new ArrayList<>();
            Folder folder1 = new Folder("moje autorskie przepisy");
            Folder folder2 = new Folder("moje ulubione przepisy");
            folders.add(folder1);
            folders.add(folder2);
            userWithId.get().setFolders(folders);
            userRepository.save(userWithId.get());
        }

        checkUser = userRepository.findByUsername("Agnieszka");
        if (!checkUser.isPresent()) {
            User user = new User("Agnieszka", "quackquack", "agnieszka.gotuje@gmail.com");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            Optional<User> userWithId = userRepository.findByUsername("Agnieszka");
            List<Folder> folders = new ArrayList<>();
            Folder folder1 = new Folder("moje autorskie przepisy");
            Folder folder2 = new Folder("moje ulubione przepisy");
            folders.add(folder1);
            folders.add(folder2);
            userWithId.get().setFolders(folders);
            userRepository.save(userWithId.get());
        }

        Optional<Recipe> checkRecipe = recipeRepository.findByName("Szybka zapiekanka z mięsem mielonym i ziemniakami");
        if (!checkRecipe.isPresent()) {
            Recipe recipe1 = new Recipe();
            recipe1.setName("Szybka zapiekanka z mięsem mielonym i ziemniakami");


            List<String> ingredients = new ArrayList<>();
            ingredients.add("mielona wołowina 500 g");
            ingredients.add("cebula 1 sztuka");
            ingredients.add("ziemniaki 2 kg");
            ingredients.add("ser żółty 220 g");
            ingredients.add("ser cheddar 100 g");
            ingredients.add("bulion 100 ml");
            ingredients.add("jajko 1 sztuka");
            ingredients.add("natka pietruszki 4 gałązki");
            ingredients.add("sól 1/2 łyżeczki");
            ingredients.add("pieprz 1/4 łyżeczki");
            ingredients.add("oliwa 3 łyżki");
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
            recipe1.setTimeMinutes(60);
            recipeRepository.save(recipe1);


            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorskie przepisy")) {
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

            List<String> ingredients = new ArrayList<>();
            ingredients.add("serek waniliowy 400 g");
            ingredients.add("jajka 2 sztuki");
            ingredients.add("mąka 1 szklanka");
            ingredients.add("proszek do pieczenia 1 łyżeczka");
            ingredients.add("olej 2 łyżki");
            recipe2.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Ubić pianę z białek.");
            steps.add("Dodać cukier.");
            steps.add("Wymieszać z żółtkami i serkiem waniliowym.");
            steps.add("Dodać proszek do pieczenia.");
            steps.add("Smażyć na oleju.");
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
            recipe2.setTimeMinutes(40);
            recipeRepository.save(recipe2);

            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorskie przepisy")) {
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

            List<String> ingredients = new ArrayList<>();
            ingredients.add("ziemniaki 0.5 kg");
            ingredients.add("mąka pszenna 0.5 łyżki");
            ingredients.add("cebula 1/4 sztuki");
            ingredients.add("jajko 1 sztuka");
            ingredients.add("sól 2 szczypty");
            ingredients.add("olej roślinny 2 łyżki");
            recipe3.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Rozetrzeć ziemniaki na tarce, zostawić na kilka minut, odlać zebrany sok.");
            steps.add("Do ziemniaków dodać mąkę, drobno startą cebulę, jajko i sól.");
            steps.add("Masę wymieszać i smażyć małe porcje ciasta na rozgrzanym oleju na złoty kolor.");
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
            recipe3.setTimeMinutes(30);
            recipeRepository.save(recipe3);

            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorskie przepisy")) {
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

