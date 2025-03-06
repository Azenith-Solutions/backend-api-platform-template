package br.com.exemplo.backendapitemplate.v1.controller;

import br.com.exemplo.backendapitemplate.v1.model.User;
import br.com.exemplo.backendapitemplate.v1.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try{
            return ResponseEntity.ok(userService.findAll());
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        try{
            return ResponseEntity.ok(userService.findById(id));
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.register(user));
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        try{
            user.setId(id);
            return ResponseEntity.ok(userService.update(user));
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        try{
            userService.delete(id);
            return ResponseEntity.ok().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
