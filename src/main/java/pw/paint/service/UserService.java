package pw.paint.service;

import pw.paint.DTOs.model.UserDto;

import java.util.List;

public interface UserService {

    void signup(UserDto userDto);

    List<UserDto> getAllUsers();
}
