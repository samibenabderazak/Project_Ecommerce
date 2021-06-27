package com.project.controller.admin;

import com.project.model.dto.UserDTO;
import com.project.service.admin.ManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManageUsersController {
    @Autowired
    private ManagerUserService userService;

    @GetMapping(value = "admin/user")
    public ResponseEntity<?> managerUser(@RequestParam(value = "page") Integer page,
                                        @RequestParam(value = "limit") Integer limit){
        List<UserDTO> list = userService.findAllUsers(page, limit);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "admin/user-count")
    public ResponseEntity<?> totalUsers(){
        return ResponseEntity.ok(userService.countUsers());
    }

    @PostMapping(value = "admin/user")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @PutMapping(value = "admin/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @DeleteMapping(value = "admin/user")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
