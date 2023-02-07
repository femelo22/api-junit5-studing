package br.com.lfmelo.apijunit5.factories;

import br.com.lfmelo.apijunit5.entities.User;
import br.com.lfmelo.apijunit5.entities.dtos.UserDTO;

import java.time.LocalDateTime;

public class UserFactory {

    public static UserDTO buildUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setName("Luiz");
        dto.setEmail("luiz@gmail.com");
        dto.setPassword("12345");
        return dto;
    }

    public static User buildNewUser() {
        User user = new User();
        user.setName("Luiz");
        user.setEmail("luiz@gmail.com");
        user.setPassword("12345");
        user.setCreated(LocalDateTime.now());
        return user;
    }

    public static User buildSavedUser() {
        User user = new User();
        user.setId(1);
        user.setName("Luiz");
        user.setEmail("luiz@gmail.com");
        user.setPassword("12345");
        user.setCreated(LocalDateTime.now());
        return user;
    }

    public static User buildUpdatedUser() {
        User user = new User();
        user.setId(1);
        user.setName("Luiz Fernando");
        user.setEmail("luiz1234@gmail.com");
        user.setPassword("12345");
        return user;
    }
}
