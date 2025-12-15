package org.kane.server.repository;

import org.kane.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    default Optional<User> getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return findByUsername(username);
    }

    Optional<User> getUserById(Long id);
}
