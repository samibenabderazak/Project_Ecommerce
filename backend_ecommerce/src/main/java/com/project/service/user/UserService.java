package com.project.service.user;

import com.project.model.dto.UserDTO;
import com.project.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO checkLogin(String username, String password);
    User findUserById(Integer id);
    UserDTO updateUser(UserDTO userDTO);
    User addUser(UserDTO userDTO);

}
