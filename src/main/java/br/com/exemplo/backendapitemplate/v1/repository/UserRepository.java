package br.com.exemplo.backendapitemplate.v1.repository;

import br.com.exemplo.backendapitemplate.v1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
