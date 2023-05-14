package pw.paint.DTOs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String userName;
    private String password;
    private String email;

}
