package pw.paint.service;

import pw.paint.DTOs.model.SignUpRequest;
import pw.paint.DTOs.model.UserDto;

import java.util.List;

public interface UserService {
    void signup(SignUpRequest signUpRequest);
    List<UserDto> getAllUsers();
}
