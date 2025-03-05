package br.com.exemplo.backendapitemplate.repository;

import br.com.exemplo.backendapitemplate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
