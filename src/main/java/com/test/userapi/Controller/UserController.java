package com.test.userapi.Controller;

import com.test.userapi.Model.LoginDTO;
import com.test.userapi.Model.User;
import com.test.userapi.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ){
        return userService.getUsers(sortedBy, filter);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);

    }
    @PatchMapping("/update/{id}")
    public User updateUser(
            @PathVariable UUID id,
            @RequestBody User user
    ){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id){

        userService.deleteUser(id);

        return "Usuario eliminado correctamente";
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDTO request){

        return userService.login(
                request.getTaxId(),
                request.getPassword()
        );
    }

}
