package pw.paint.DTOs.mappers;

import pw.paint.DTOs.model.UserDto;
import pw.paint.model.User;

public class UserMapper {
    public static UserDto toUserDto (User user){
        return new UserDto(user.getUsername(),user.getPassword(),user.getEmail());
    }
}
