package de.abramov.backend.rest.repository;

import de.abramov.backend.rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}