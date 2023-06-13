package pw.paint;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pw.paint.model.*;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.TagRepository;
import pw.paint.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        addTags();
        addUsers();

        // Przepisy Ani
        User user = userRepository.findByUsername("Ania").get();
        Optional<Recipe> checkRecipe = recipeRepository.findByName("Szybka zapiekanka z mięsem mielonym i ziemniakami");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Szybka zapiekanka z mięsem mielonym i ziemniakami",user,true, 60);

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
            recipe.setIngredients(ingredients);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\recipe1.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<String> steps = new ArrayList<>();
            steps.add("Cebulę obierz i drobno posiekaj.");
            steps.add("Na patelni rozgrzej oliwę. Dodaj wołowinę i cebulę. Smaż wszystko przez kilka minut do zrumienienia. Dopraw solą i pieprzem. W razie potrzeby odcedź tłuszcz.");
            steps.add("Duże naczynie żaroodporne nasmaruj oliwą. Na dnie połóż ok. 1/5 ziemniaków, a na to ok. ¼ wołowiny. Posyp odrobiną tartego sera żółtego. Powtarzaj te warstwy do wykończenia składników.");
            steps.add("W garnuszku wymieszaj bulion z mlekiem. Dodaj tarty ser cheddar i podgrzewaj wszystko, stale mieszając, do rozpuszczenia sera. Zdejmij z ognia i ostudź. Dodaj jajko i wymieszaj. Mieszanką zalej zapiekankę.");
            steps.add("Na wierzchu poukładaj odłożone wcześniej ziemniaki, by lekko na siebie zachodziły. Posyp siekaną natką pietruszki.");
            steps.add("Piekarnik nagrzej do 180 st. Celsjusza. Naczynie przykryj folią aluminiową. Szybką zapiekankę z mięsem mielonym i ziemniakami piecz przez 60 minut, do miękkości ziemniaków. Usuń folię i kontynuuj pieczenie przez kolejne 20 minut, do zrumienienia.");
            recipe.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Zapiekanka ziemniaczana");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Zapiekanka ziemniaczana",user,true, 60);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Ziemniaki  1 kg");
            ingredients.add("Cebula 1 duża sztuka");
            ingredients.add("Ser żółty 200 g");
            ingredients.add("Śmietana kwaśna  200 ml");
            ingredients.add("Jajka  2 sztuki");
            ingredients.add("Sól szczypta");
            ingredients.add("Pieprz szczypta");
            recipe.setIngredients(ingredients);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\recipe2.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<String> steps = new ArrayList<>();
            steps.add("Ziemniaki należy obrać i umyć. Następnie zetrzeć je na tarce o drobnych oczkach lub pokroić na bardzo cienkie plasterki.");
            steps.add("Cebulę drobno posiekać.");
            steps.add("W rondlu rozgrzać niewielką ilość masła i podsmażyć cebulę, aż będzie miękka i lekko zrumieniona.");
            steps.add("W misce wymieszać ziemniaki z podsmażoną cebulą. Dodaj ser żółty, śmietanę kwaśną oraz jajka. Całość dokładnie wymieszać.");
            steps.add("Doprawić masę solą i pieprzem według własnego gustu. Warto pamiętać, że ziemniaki mogą wymagać nieco więcej soli, aby smak był wyraźny.");
            steps.add("Następnie, formę do zapiekania należy natłuścić masłem. Jeśli nie masz specjalnej formy do zapiekania, możesz użyć zwykłej naczyniowej lub żaroodpornej.");
            steps.add("Przełożyć masę ziemniaczaną do natłuszczonej formy, równomiernie rozprowadzając.");
            steps.add("Wstawić formę do nagrzanego piekarnika (180°C) i piec zapiekankę przez około 45-50 minut lub do momentu, gdy ziemniaki będą miękkie, a wierzch lekko zrumieniony.");
            steps.add("Po upieczeniu, wyjąć zapiekankę z piekarnika i dać jej chwilę ostygnąć, aby ściągnęła się i nabierała konsystencji.");
            recipe.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("polskie").get();tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();tags.add(tag);
            tag = tagRepository.findByName("obiad").get();tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Tradycyjny piernik");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Tradycyjny piernik",user,true, 50);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Mąka pszenna  2 i 1/2 szklanki");
            ingredients.add("Cukier 1 szklanka");
            ingredients.add("Miodu 3/4 szklanki");
            ingredients.add("Masło 1/2 szklanki (rozpuszczone i lekko ostudzone)");
            ingredients.add("Jajka 2 sztuki");
            ingredients.add("Kakao 2 łyżki");
            ingredients.add("Cynamon 2 łyżeczki");
            ingredients.add("Imbir 1 łyżeczka");
            ingredients.add("Goździki 1/2 łyżeczki");
            ingredients.add("Sól 1/4 łyżeczki");
            ingredients.add("Proszek do pieczenia 1 łyżeczka");
            ingredients.add("Soda oczyszczona 1/2 łyżeczki");
            ingredients.add("Migdały 1/2 szklanki (posiekane)");
            ingredients.add("Rodzynki 1/2 szklanki");

            recipe.setIngredients(ingredients);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\recipe3.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<String> steps = new ArrayList<>();
            steps.add("W dużym naczyniu wymieszaj mąkę, cukier, kakao, cynamon, imbir, goździki, sól, proszek do pieczenia i sodę oczyszczoną. Dokładnie wymieszaj wszystkie suche składniki.");
            steps.add("W innym naczyniu połącz miód, rozpuszczone masło i jajka. Ubij składniki trzepaczką, aż masa będzie gładka.");
            steps.add("Powoli wlej składniki mokre do naczynia z suchymi składnikami. Wymieszaj je za pomocą drewnianej łyżki lub miksera, aż powstanie jednolita masa.");
            steps.add("Dodaj posiekane migdały oraz rodzynki. Wymieszaj je równomiernie, aby były rozłożone w całym cieście.");
            steps.add("Przygotuj prostokątną formę do pieczenia, wyłożoną papierem do pieczenia lub natłuść ją masłem i posyp mąką, aby zapobiec przywieraniu ciasta.");
            steps.add("Przelej przygotowaną masę do formy, równomiernie rozprowadzając.");
            steps.add("Piecz piernik w nagrzanym piekarniku do temperatury 180°C przez około 40-45 minut. Sprawdź, czy jest gotowy, wkłuwając w środek patyczek. Jeśli patyczek wyjdzie suchy, piernik jest gotowy. Jeśli nie, kontynuuj pieczenie przez kilka minut i sprawdzaj co jakiś czas.");
            steps.add("Wyjmij piernik z piekarnika i pozostaw go w formie przez kilka minut, a następnie przenieś na kratkę do ostygnięcia.");
            steps.add("Gdy piernik całkowicie ostygnie, możesz go pokroić na kawałki i podawać. Jeśli chcesz, możesz posypać go cukrem pudrem przed podaniem.");
            recipe.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("deser").get();tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Placki z serkiem");
        if (!checkRecipe.isPresent()) {
            Recipe recipe5 = new Recipe();
            recipe5.setName("Placki z serkiem");

            List<String> ingredients = new ArrayList<>();
            ingredients.add("serek waniliowy 400 g");
            ingredients.add("jajka 2 sztuki");
            ingredients.add("mąka 1 szklanka");
            ingredients.add("proszek do pieczenia 1 łyżeczka");
            ingredients.add("olej 2 łyżki");
            recipe5.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Ubić pianę z białek.");
            steps.add("Dodać cukier.");
            steps.add("Wymieszać z żółtkami i serkiem waniliowym.");
            steps.add("Dodać proszek do pieczenia.");
            steps.add("Smażyć na oleju.");
            recipe5.setSteps(steps);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\placki.jpg";
            Path filePath = Paths.get(imagePath);
            recipe5.setImage(Files.readAllBytes(filePath));

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
                recipe5.setAuthor(userWithId.get());
            }

            recipe5.setTags(tags);
            recipe5.setStatus(true);
            recipe5.setTimeMinutes(40);
            recipeRepository.save(recipe5);

            List<Folder> folders2 = userWithId.get().getFolders();

            for (Folder folder2 : folders2) {
                if (folder2.getName().equals("moje autorskie przepisy")) {
                    if (folder2.getRecipes() == null) {
                        folder2.setRecipes(new ArrayList<>());
                        folder2.getRecipes().add(recipe5);

                    } else {
                        folder2.getRecipes().add(recipe5);
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

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\placki2.jpg";
            Path filePath = Paths.get(imagePath);
            recipe3.setImage(Files.readAllBytes(filePath));

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


        checkRecipe = recipeRepository.findByName("Klasyczne Lasagne Bolognese");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe();
            recipe.setName("Klasyczne Lasagne Bolognese");

            List<String> ingredients = new ArrayList<>();
            ingredients.add("500g mielonej wołowiny");
            ingredients.add("500g mielonego wieprzowiny");
            ingredients.add("1 cebula, drobno posiekana");
            ingredients.add("2 ząbki czosnku, posiekane");
            ingredients.add("2 marchewki, drobno pokrojone");
            ingredients.add("2 łodygi selera, drobno pokrojone");
            ingredients.add("1 puszka pomidorów krojonych");
            ingredients.add("2 łyżki koncentratu pomidorowego");
            ingredients.add("1 łyżeczka suszonego oregano");
            ingredients.add("1 łyżeczka suszonego tymianku");
            ingredients.add("1 łyżeczka suszonej bazylii");
            ingredients.add("1 łyżeczka cukru");
            ingredients.add("250ml czerwonego wina");
            ingredients.add("250ml bulionu wołowego");
            ingredients.add("250g sera ricotta");
            ingredients.add("100g startego parmezanu");
            ingredients.add("250g makaronu lasagne");
            ingredients.add("2 łyżki oliwy z oliwek");
            ingredients.add("Sól i pieprz do smaku");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("W dużym garnku rozgrzej oliwę z oliwek. Dodaj posiekaną cebulę, czosnek, marchewki i seler. Smaż na średnim ogniu przez około 5 minut, aż warzywa zmiękną.");
            steps.add("Dodaj mielone mięso wołowe i wieprzowe do garnka. Smaż, mieszając, aż mięso będzie dobrze podsmażone i rozdrobnione.");
            steps.add("Dodaj pomidory krojone, koncentrat pomidorowy, suszone zioła, cukier, czerwone wino i bulion wołowy. Doprowadź do wrzenia, a następnie zmniejsz ogień i gotuj na wolnym ogniu przez około 1,5 godziny. Mieszaj od czasu do czasu.");
            steps.add("W międzyczasie gotuj makaron lasagne według instrukcji na opakowaniu, aż będzie al dente. Odcedź i odstaw na bok.");
            steps.add("Przygotuj formę do zapiekania. Na dno formy nałóż cienką warstwę sosu mięsnego. Następnie ułóż jedną warstwę makaronu lasagne. Na makaron nałóż część sera ricotta i posyp startym parmezanem.");
            steps.add("Kontynuuj układanie warstw, używając pozostałego sosu mięsnego, makaronu lasagne, sera ricotta i parmezanu. Powinno wystarczyć na 3-4 warstwy.");
            steps.add("Zakończ na wierzchu sosu mięsnego i posyp dodatkowym parmezanem.");
            steps.add("Zapiekaj lasagne w nagrzanym piekarniku do temperatury 180°C przez około 30-35 minut, aż wierzch będzie złocisty i całość dobrze się zespoli.");
            steps.add("Po upieczeniu wyjmij z piekarnika i pozostaw do ostygnięcia przez kilka minut przed podaniem. Smacznego!");
            recipe.setSteps(steps);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\lasagne.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Optional<Tag> tag = tagRepository.findByName("obiad");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("włoskie");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            Optional<User> userWithId = userRepository.findByUsername("Filip");
            if (userWithId.isPresent()) {
                recipe.setAuthor(userWithId.get());
            }

            recipe.setTags(tags);
            recipe.setStatus(true);
            recipe.setTimeMinutes(150);
            recipeRepository.save(recipe);

            List<Folder> folders = userWithId.get().getFolders();

            for (Folder folder : folders) {
                if (folder.getName().equals("moje autorskie przepisy")) {
                    if (folder.getRecipes() == null) {
                        folder.setRecipes(new ArrayList<>());
                        folder.getRecipes().add(recipe);

                    } else {
                        folder.getRecipes().add(recipe);
                    }
                }
            }
            userRepository.save(userWithId.get());

        }

        checkRecipe = recipeRepository.findByName("Sałatka Caprese");
        if (!checkRecipe.isPresent()) {
            Recipe recipe2 = new Recipe();
            recipe2.setName("Sałatka Caprese");

            List<String> ingredients2 = new ArrayList<>();
            ingredients2.add("4 pomidory");
            ingredients2.add("250g mozzarelli");
            ingredients2.add("1 pęczek świeżej bazylii");
            ingredients2.add("2 łyżki oliwy z oliwek");
            ingredients2.add("Sól i pieprz do smaku");
            recipe2.setIngredients(ingredients2);

            List<String> steps2 = new ArrayList<>();
            steps2.add("Pokrój pomidory na plastry, a mozzarellę na cienkie plasterki.");
            steps2.add("Ułóż na półmisku warstwę pomidorów, następnie dodaj plasterki mozzarelli.");
            steps2.add("Posyp warstwę bazylią i przypraw solą i pieprzem.");
            steps2.add("Powtórz kolejne warstwy pomidorów, mozzarelli i bazylii.");
            steps2.add("Na wierzch skrop sałatkę oliwą z oliwek.");
            steps2.add("Przykryj i schłodź w lodówce przez około 30 minut przed podaniem.");
            recipe2.setSteps(steps2);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\caprese.jpg";
            Path filePath = Paths.get(imagePath);
            recipe2.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Optional<Tag> tag = tagRepository.findByName("przekąska");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("włoskie");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("wegetariańskie");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            Optional<User> userWithId = userRepository.findByUsername("Filip");
            if (userWithId.isPresent()) {
                recipe2.setAuthor(userWithId.get());
            }

            recipe2.setTags(tags);
            recipe2.setStatus(true);
            recipe2.setTimeMinutes(15);
            recipeRepository.save(recipe2);

            List<Folder> folders = userWithId.get().getFolders();

            for (Folder folder : folders) {
                if (folder.getName().equals("moje autorskie przepisy")) {
                    if (folder.getRecipes() == null) {
                        folder.setRecipes(new ArrayList<>());
                        folder.getRecipes().add(recipe2);

                    } else {
                        folder.getRecipes().add(recipe2);
                    }
                }
            }
            userRepository.save(userWithId.get());
        }

        checkRecipe = recipeRepository.findByName("Tacos z Kurczakiem");
        if (!checkRecipe.isPresent()) {
            Recipe recipe3 = new Recipe();
            recipe3.setName("Tacos z Kurczakiem");

            List<String> ingredients3 = new ArrayList<>();
            ingredients3.add("500g filetów z kurczaka, pokrojonych na paski");
            ingredients3.add("1 cebula, pokrojona w kostkę");
            ingredients3.add("2 ząbki czosnku, posiekane");
            ingredients3.add("1 papryka jalapeno, posiekana (opcjonalnie)");
            ingredients3.add("2 łyżki soku z limonki");
            ingredients3.add("2 łyżeczki papryki w proszku");
            ingredients3.add("1 łyżeczka kuminu");
            ingredients3.add("1 łyżeczka oregano");
            ingredients3.add("Sól i pieprz do smaku");
            ingredients3.add("Tortille");
            ingredients3.add("Salsa i guacamole do podania");
            recipe3.setIngredients(ingredients3);

            List<String> steps3 = new ArrayList<>();
            steps3.add("W misce wymieszaj kurczaka, cebulę, czosnek, paprykę jalapeno (opcjonalnie), sok z limonki, paprykę w proszku, kumin, oregano, sól i pieprz.");
            steps3.add("Odstaw na około 30 minut, aby marynować.");
            steps3.add("Na rozgrzaną patelnię wrzuć kurczaka wraz z marynatą. Smaż na średnim ogniu przez około 6-8 minut, aż kurczak będzie dobrze przyrumieniony i dobrze ugotowany.");
            steps3.add("Podgrzej tortille na suchej patelni przez kilka sekund z każdej strony, aby stały się miękkie i elastyczne.");
            steps3.add("Nałóż na każdą tortillę smażony kurczak i dodatki takie jak salsa i guacamole.");
            steps3.add("Zroluj tortille i podawaj od razu.");
            recipe3.setSteps(steps3);

            String imagePath = "..\\przepisnik\\src\\main\\resources\\static\\init_jpg\\tacos.jpg";
            Path filePath = Paths.get(imagePath);
            recipe3.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Optional<Tag> tag = tagRepository.findByName("kolacja");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("meksykańskie");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            tag = tagRepository.findByName("ostre");
            if (tag.isPresent()) {
                tags.add(tag.get());
            }

            Optional<User> userWithId = userRepository.findByUsername("Filip");
            if (userWithId.isPresent()) {
                recipe3.setAuthor(userWithId.get());
            }

            recipe3.setTags(tags);
            recipe3.setStatus(true);
            recipe3.setTimeMinutes(40);
            recipeRepository.save(recipe3);

            List<Folder> folders = userWithId.get().getFolders();

            for (Folder folder : folders) {
                if (folder.getName().equals("moje autorskie przepisy")) {
                    if (folder.getRecipes() == null) {
                        folder.setRecipes(new ArrayList<>());
                        folder.getRecipes().add(recipe3);

                    } else {
                        folder.getRecipes().add(recipe3);
                    }
                }
            }
            userRepository.save(userWithId.get());
        }
    }


    private void addTags(){
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
    }

    private void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Optional<User> userWithId = userRepository.findByUsername(user.getUsername());
        List<Folder> folders = new ArrayList<>();
        Folder folder1 = new Folder("moje autorskie przepisy");
        Folder folder2 = new Folder("moje ulubione przepisy");
        folders.add(folder1);
        folders.add(folder2);
        userWithId.get().setFolders(folders);
        userRepository.save(userWithId.get());
    }

    private void addUsers(){
        Optional<User> checkUser = userRepository.findByUsername("Ania");
        if (!checkUser.isPresent()) {
            User user = new User("Ania", "tajnehaslo", "ania.gotuje@gmail.com");
            addUser(user);

        }

        checkUser = userRepository.findByUsername("Agnieszka");
        if (!checkUser.isPresent()) {
            User user = new User("Agnieszka", "quackquack", "agnieszka.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("Filip");
        if (!checkUser.isPresent()) {
            User user = new User("Filip", "filiptubyl", "filip.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("Zuzanna");
        if (!checkUser.isPresent()) {
            User user = new User("Zuzanna", "aaa111aaa", "zuzanka.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("grzesiu");
        if (!checkUser.isPresent()) {
            User user = new User("grzesiu", "grzesiu", "grzesiu.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("Dorota");
        if (!checkUser.isPresent()) {
            User user = new User("Dorota", "password", "dorota.gotuje@gmail.com");
            addUser(user);
        }
    }

    private void addToFolder(User user, Recipe recipe){
        List<Folder> folders = user.getFolders();

        for (Folder folder : folders) {
            if (folder.getName().equals("moje autorskie przepisy")) {
                if (folder.getRecipes() == null) {
                    folder.setRecipes(new ArrayList<>());
                    folder.getRecipes().add(recipe);

                } else
                    folder.getRecipes().add(recipe);
            }
        }
        userRepository.save(user);
    }
}

