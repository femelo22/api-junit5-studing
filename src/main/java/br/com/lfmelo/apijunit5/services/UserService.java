package br.com.lfmelo.apijunit5.services;

import br.com.lfmelo.apijunit5.entities.User;
import br.com.lfmelo.apijunit5.entities.dtos.UserDTO;

public interface UserService {

    User registerUser(User user);

    User findUserById(Integer id);

    void updateUser(Integer id, UserDTO dto);

    void deleteUser(Integer id);
}
