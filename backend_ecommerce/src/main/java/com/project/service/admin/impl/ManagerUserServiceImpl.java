package com.project.service.admin.impl;

import com.project.model.dto.UserDTO;
import com.project.model.mapper.UserMapper;
import com.project.model.response.ResponseNormal;
import com.project.repository.user.OrderRepository;
import com.project.repository.user.RoleRepository;
import com.project.repository.user.UserRoleRepository;
import com.project.entity.Orders;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.entity.UserRole;
import com.project.repository.user.UserRepository;
import com.project.service.admin.ManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManagerUserServiceImpl implements ManagerUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserDTO> findAllUsers(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<User> users = userRepository.findAll(pageable).getContent();
        List<UserDTO> result = new ArrayList<>();
        for(User x : users){
            result.add(UserMapper.toUserDTO(x));
        }
        return result;
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public ResponseNormal addUser(UserDTO userDTO) {
        if(checkDuplicateUsername(userDTO)){
            return new ResponseNormal(
                    "Username: "+userDTO.getUsername()+" already exists, add new failed!",
                    HttpStatus.BAD_REQUEST);
        }

        // Hash password using BCrypt
        String hash = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12));
        User user = UserMapper.toUser(userDTO);
        user.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setPassword(hash);
        //Save
        User newUser = userRepository.save(user);
        //Save the new user's role_users
        for(String s: userDTO.getRoles()){
            Role role = roleRepository.findByName(s).get();
            UserRole userRole = new UserRole();
            userRole.setUserByIdUser(newUser);
            userRole.setRoleByIdRole(role);
            userRoleRepository.save(userRole);
        }
        return new ResponseNormal("Successfully added new", HttpStatus.OK);
    }

    @Override
    public ResponseNormal deleteUser(Integer id) {
        //Users who have ordered before cannot delete it
        if(checkUserHasOrder(id)){
           return new ResponseNormal("The user already has an order, which cannot be deleted!",HttpStatus.BAD_REQUEST);
        }
        List<UserRole> userRoleList = userRoleRepository.findByIdUser(id);
        if(userRoleList.size() > 0){
            for(UserRole x : userRoleList){
                userRoleRepository.delete(x);
            }
        }
        userRepository.deleteById(id);
        return new ResponseNormal("Delete successfully",HttpStatus.OK);
    }

    @Override
    public ResponseNormal updateUser(UserDTO userDTO) {
        if(checkDuplicateUsername(userDTO)){
            return new ResponseNormal(
                    "Username: "+userDTO.getUsername()+" already exists, fix failed!",
                    HttpStatus.BAD_REQUEST);
        }
        //Get the list of old and new roles sorted and compare the change
        boolean changed = false;
        Collection<String> oldRoles= UserMapper.toUserDTO(userRepository.findById(userDTO.getId()).get()).getRoles();
        List<String> oldListRole = oldRoles.stream().collect(Collectors.toList());
        List<String> newListRole = userDTO.getRoles().stream().collect(Collectors.toList());
        if(oldListRole.size() == newListRole.size()){
            Collections.sort(oldListRole);
            Collections.sort(newListRole);
            for(int i=0; i<oldListRole.size(); i++){
                if(!oldListRole.get(i).equals(newListRole.get(i))){
                    changed = true;
                }
            }
        }else {
            changed = true;
        }

        //Save information submitted by userDTO
        User user = userRepository.save(UserMapper.toUser(userDTO));

        //If there is a role change, fix it
        if(changed){
            //Delete all user roles of the user and reassign
            List<UserRole> userRoleList= userRoleRepository.findByIdUser(userDTO.getId());
            for(UserRole x : userRoleList){
                userRoleRepository.delete(x);
            }
            if(userDTO.getRoles().size()>0){
                for(String s : userDTO.getRoles()){
                    UserRole userRole = new UserRole();
                    userRole.setRoleByIdRole(roleRepository.findByName(s).get());
                    userRole.setUserByIdUser(user);
                    userRoleRepository.save(userRole);
                }
            }
        }
        return new ResponseNormal("Cập nhật thành công!", HttpStatus.OK);
    }

    //Check if the user has an unpaid order
    public boolean checkUserHasOrder(Integer id){
        List<Orders> orders = orderRepository.findAllByIdUser(id);
        if(orders.size() > 0){
            return true;
        }
        return false;
    }

    //Check trùng username trước khi sửa
    public boolean checkDuplicateUsername(UserDTO userDTO){
        List<User> list = userRepository.findAll();
        for(User u : list){
            if(u.getUsername().equals(userDTO.getUsername())
                    &&
                    !u.getId().equals(userDTO.getId()) ){
                return true;
            }
        }
        return false;
    }
}
