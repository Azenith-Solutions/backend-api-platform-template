package br.com.exemplo.backendapitemplate.v1.controller;

import br.com.exemplo.backendapitemplate.v1.model.User;
import br.com.exemplo.backendapitemplate.v1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users API - v1", description = "Gerenciamento de usuários | Endpoints")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Buscar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados na base de dados")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try{
            List<User> usersFound = userService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(usersFound);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar usuário pelo ID", description = "Retorna apenas um usuário encontrado pelo ID na base de dados")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try{
            User userFound = userService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(userFound);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Registrar um usuário", description = "Registra um usuário na base de dados")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            User userCreated = userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(EntityExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Atualizar um usuário pelo ID", description = "Atualiza os dados de um usuário através do ID encontrado na base de dados")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user) {
        try{
            user.setId(id);
            User userUpdated = userService.update(user);
            return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(EntityExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Deletar um usuário pelo ID", description = "Deleta um usuário pelo ID do usuário encontrado na base de dados")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try{
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso!");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
