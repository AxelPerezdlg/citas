package mx.edu.utez.scct.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.WebSecurityConfig;
import mx.edu.utez.scct.entity.User;
import mx.edu.utez.scct.repository.UserRepository;
import mx.edu.utez.scct.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSecurityConfig config;

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> usuario = userRepository.findById(username);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(config.passwordEncoder().encode(user.getPassword()));
        User usuario = userRepository.save(user);
        if (usuario != null) {
            return usuario;
        }
        return null;
    }

    @Override
    public Boolean deleteUser(String username) {
        Boolean response = false;
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            response = true;
        }
        return response;
    }

}
