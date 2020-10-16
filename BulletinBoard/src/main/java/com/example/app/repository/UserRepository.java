package com.example.app.repository;

import com.example.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    User save(User user);
    boolean existsUserByEmail(String email);
    void deleteById(Long id);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String username);
}
