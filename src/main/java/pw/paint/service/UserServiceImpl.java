package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.FolderMapper;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.mappers.UserMapper;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.UserDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.User;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toUserDto(user));
        }
        return userDtos;
    }

    @Override
    public String createNewFolder(String userName, String folderName) {

        Optional<User> user = userRepository.findByUsername(userName);

        if(!user.isPresent()){
            return "Nie ma takiego użytkownika";
        }

        List<Folder> userFolders = user.get().getFolders();
        for(Folder folder : userFolders){
            if(folder.getName().equals(folderName)){
                return "Folder o takiej nazwie już istnieje";
            }
        }

        user.get().getFolders().add(new Folder(folderName));

        userRepository.save(user.get());


        return "Utworzono nowy folder";
    }

    @Override
    public List<RecipeDto> getUserRecipes(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            return null;

        List<Folder> folders = user.get().getFolders();
        if(folders.isEmpty())
            return null;

        List<Recipe> recipes = new ArrayList<>();
        for (Folder folder : folders) {
            recipes.addAll(folder.getRecipes());
        }
        if(recipes.isEmpty())
            return null;

        List<RecipeDto> recipeDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDto.add(RecipeMapper.toRecipeDto(recipe));
        }
        return recipeDto;
    }

    @Override
    public UserDto getUserById(ObjectId id) {
        System.out.println(id);
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            return null;
        return UserMapper.toUserDto(user.orElse(null));
    }

    @Override
    public List<FolderDto> getFolders(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            return null;
        List<FolderDto> folders = new ArrayList<>();
        for (Folder folder : user.get().getFolders()) {
            folders.add(FolderMapper.toFolderDto(folder));
        }
        return folders;
    }

    @Override
    public List<RecipeDto> getFolderRecipes(UserDto userDto, String name) {
        Optional<User> user = userRepository.findByUsername(userDto.getUserName());
        if(user.isEmpty())
            return null;

        List<RecipeDto> recipes = new ArrayList<>();
        for (Folder folder : user.get().getFolders()) {
            if (folder.getName().equals(name)) {
                for (Recipe recipe : folder.getRecipes()) {
                    Optional<Recipe> r = recipeRepository.findById(recipe.getId());
                    if (r.isEmpty())
                        continue;
                    recipes.add(RecipeMapper.toRecipeDto(r.orElse(null)));
                }
                return recipes;
            }
        }
        return recipes;
    }

    @Override
    public String addToFolder(RecipeDto recipeDto, String folderName) {

        Optional<User> user = userRepository.findByUsername(recipeDto.getAuthor());
        if(user.isEmpty())
            return "Nie znaleziono Użytkownika";

        Recipe recipe = Recipe.builder()
                .id(new ObjectId(recipeDto.getId()))
                .name(recipeDto.getName())
                .ingredients(recipeDto.getIngredients())
                .steps(recipeDto.getSteps())
                .status(recipeDto.getStatus())
                .timeMinutes(recipeDto.getTimeMinutes())
                .author(user.orElse(null))
                .build();

        recipeRepository.save(recipe);

        for (Folder folder : user.get().getFolders()) {
            if (folder.getName().equals(folderName)) {
                folder.getRecipes().add(recipe);
                userRepository.save(user.get());
                return "Dodano przepis";
            }
        }

        return "Nie znaleziono folderu";
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails u = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("ni ma"));
        System.out.println(u.getUsername());
        System.out.println(u.getPassword());
        return u;
    }


}
