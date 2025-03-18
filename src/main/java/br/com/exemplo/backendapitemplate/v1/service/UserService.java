package br.com.exemplo.backendapitemplate.v1.service;

import br.com.exemplo.backendapitemplate.v1.model.User;
import br.com.exemplo.backendapitemplate.v1.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new EntityNotFoundException("Usuários não encontrado.");
        }

        try{
            return userRepository.findAll();
        }catch(Exception e){
            throw new RuntimeException("Ocorreu um erro ao buscar todos os usuários: "+e);
        }
    }

    public User findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Parâmetros inválidos ou nulos.");
        }

        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        try{
            return userRepository.findById(id).get();
        }catch(Exception e){
            throw new RuntimeException("Ocorreu um erro ao encontrar o usuário: "+e);
        }
    }

    public User register(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário não pode ser nulo ou vazio.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Senha do usuário não pode ser nulo ou vazio.");
        }

        if(userRepository.findUserByEmail(user.getEmail()) != null){
            throw new EntityExistsException("Conflito com um usuário existente. Email em uso.");
        }

        try{
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao registrar usuário: "+e);
        }
    }

    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Informações do usuários nulo ou vazia.");
        }

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        User userFound = optionalUser.get();

        User probableUserConflict = userRepository.findUserByEmail(user.getEmail());
        if (probableUserConflict != null && !probableUserConflict.getId().equals(userFound.getId())) {
            throw new EntityExistsException("Conflito com um usuário existente. Email em uso.");
        }

        try{
            userFound.setName(user.getName());
            userFound.setEmail(user.getEmail());
            userFound.setPassword(user.getPassword());
            userFound.setUpdatedAt(LocalDateTime.now());
            return  userRepository.save(userFound);
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao atualizar o usuário: "+e);
        }

    }

    public void delete(Long id) {
        if(id == null){
            throw new EntityNotFoundException("Parâmetros inválidos ou nulos.");
        }

        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao deletar o usuário: "+e);
        }
    }


}
