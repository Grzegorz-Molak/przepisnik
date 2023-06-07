package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.mappers.UserMapper;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.UserDto;
import pw.paint.exception.UserNotFoundException;
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
    public List<RecipeDto> getUserRecipes(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            return null;

        List<Folder> folders = user.get().getFolders();
        if (folders.isEmpty())
            return null;

        List<Recipe> recipes = new ArrayList<>();
        for (Folder folder : folders) {
            recipes.addAll(folder.getRecipes());
        }
        if (recipes.isEmpty())
            return null;

        List<RecipeDto> recipeDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDto.add(RecipeMapper.toRecipeDto(recipe));
        }
        return recipeDto;
    }

    @Override
    public UserDto getUserById(ObjectId id){
        System.out.println(id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException();
        return UserMapper.toUserDto(user.orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails u = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(u.getUsername());
        System.out.println(u.getPassword());
        return u;
    }

    //Strefa testów do usnięcia  pózniej
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toUserDto(user));
        }
        return userDtos;
    }
}
