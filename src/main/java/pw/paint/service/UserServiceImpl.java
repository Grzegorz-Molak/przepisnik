package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.UserMapper;
import pw.paint.DTOs.model.SignUpRequest;
import pw.paint.DTOs.model.UserDto;
import pw.paint.model.Folder;
import pw.paint.model.User;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUserName());
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);

    }

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
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails u = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("ni ma"));
        System.out.println(u.getUsername());
        System.out.println(u.getPassword());
        return u;
    }


}
