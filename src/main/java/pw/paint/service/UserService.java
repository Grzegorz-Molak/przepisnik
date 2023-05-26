package pw.paint.service;

import org.bson.types.ObjectId;
import pw.paint.DTOs.model.SignUpRequest;
import pw.paint.DTOs.model.UserDto;

import java.util.List;

public interface UserService {
    void signup(SignUpRequest signUpRequest);
    List<UserDto> getAllUsers();

    String createNewFolder(String userName, String folderName);
}
