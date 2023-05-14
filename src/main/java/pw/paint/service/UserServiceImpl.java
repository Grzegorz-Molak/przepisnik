package pw.paint.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.model.FolderDto;
import pw.paint.DTOs.model.UserDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.User;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signup(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            if (user.getFolders() != null) {
                List<FolderDto> folderDtos = new ArrayList<>();
                for (Folder folder : user.getFolders()) {
                    FolderDto folderDto = new FolderDto();
                    folderDto.setName(folder.getName());
                    if(folder.getRecipes() != null) {
                        List<ObjectId> recipeIds = new ArrayList<>();
                        for (Recipe recipe : folder.getRecipes()) {
                            recipeIds.add(recipe.getId());
                        }
                        folderDto.setRecipes(recipeIds);
                    }
                    folderDtos.add(folderDto);
                    userDto.setFolders(folderDtos);
                }
            }

            userDto.setId(user.getId());
            userDto.setUserName(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setEmail(user.getEmail());
            userDtos.add(userDto);
        }
        return userDtos;

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
