package br.com.lfmelo.apijunit5.services.impl;

import br.com.lfmelo.apijunit5.entities.User;
import br.com.lfmelo.apijunit5.entities.dtos.UserDTO;
import br.com.lfmelo.apijunit5.repositories.UserRepository;
import br.com.lfmelo.apijunit5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User registerUser(User user) {
        return repository.save(user);
    }

    @Override
    public User findUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public void updateUser(Integer id, UserDTO dto) {
        User user = findUserById(id);
        user.setName(dto.getName() == null ? user.getName() : dto.getName());
        user.setEmail(dto.getEmail() == null ? user.getEmail() : dto.getEmail());
        user.setPassword(dto.getPassword() == null ? user.getPassword() : dto.getPassword());
        repository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = findUserById(id);
        repository.delete(user);
    }
}
