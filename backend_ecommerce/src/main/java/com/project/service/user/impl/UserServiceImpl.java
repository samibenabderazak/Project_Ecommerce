package com.project.service.user.impl;

import com.project.model.mapper.UserMapper;
import com.project.repository.user.RoleRepository;
import com.project.repository.user.UserRoleRepository;
import com.project.entity.User;
import com.project.entity.UserRole;
import com.project.model.dto.UserDTO;
import com.project.repository.user.UserRepository;
import com.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDTO checkLogin(String username, String password) {
        if(userRepository.checkUser(username, password).isPresent()){
            User user = userRepository.checkUser(username, password).get();
            return UserMapper.toUserDTO(user);
        }
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        return (userRepository.findById(id).get());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User  user = userRepository.findById(userDTO.getId()).get();
        user.setAddress(userDTO.getAddress());
        user.setFullname(userDTO.getFullname());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return UserMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public User addUser(UserDTO userDTO) {
        //Set creation date
        userDTO.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12));
        userDTO.setPassword(hash);
        //Save user
        User user = userRepository.save(UserMapper.toUser(userDTO));
        UserRole userRole= new UserRole();
        userRole.setUserByIdUser(user);
        userRole.setRoleByIdRole(roleRepository.findById(2).get());
        userRoleRepository.save(userRole);
        Collection<UserRole> listRole = new ArrayList<>();
        listRole.add(userRole);
        user.setUserRolesById(listRole);
        return user;
    }
}
