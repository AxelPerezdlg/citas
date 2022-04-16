package mx.edu.utez.scct.service;

import java.util.List;

import mx.edu.utez.scct.entity.User;

public interface UserService {

    List<User> listAllUsers();

    User findUserByUsername(String username);

    User saveUser(User user);

    Boolean deleteUser(String username);
}
