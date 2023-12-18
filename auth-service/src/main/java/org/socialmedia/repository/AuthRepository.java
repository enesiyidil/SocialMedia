package org.socialmedia.repository;

import org.socialmedia.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Boolean existsByEmail(String email);

    Optional<Auth> findByEmailAndPassword(String email, String password);

    Optional<Auth> findByIdAndActivationCode(Long id, String activationCode);
}
