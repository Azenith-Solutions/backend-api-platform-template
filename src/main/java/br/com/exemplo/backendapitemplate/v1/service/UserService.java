package br.com.exemplo.backendapitemplate.v1.service;

import br.com.exemplo.backendapitemplate.v1.model.User;
import br.com.exemplo.backendapitemplate.v1.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        // validating parameters received
        if(id == null){
            throw new IllegalArgumentException("Parâmetros inválidos ou nulos");
        }

        // if object exists
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        // mapping any errors
        try{
            return userRepository.findById(id).get();
        }catch(EntityNotFoundException e){
            throw new RuntimeException("Ocorreu um erro ao encontrar o usuário: "+e);
        }
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        if(user.getId() == null){
            throw new IllegalArgumentException("Parâmetros inválidos ou nulos");
        }


        if(!userRepository.existsById(user.getId())){
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        try{
            return userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao atualizar o usuário: "+e);
        }
    }

    public void delete(Long id) {
        if(id == null){
            throw new EntityNotFoundException("Parâmetros inválidos ou nulos");
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
