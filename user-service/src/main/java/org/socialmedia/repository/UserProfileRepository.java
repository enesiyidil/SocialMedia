package org.socialmedia.repository;

import org.socialmedia.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);

    Optional<UserProfile> findByAuthId(Long id);

    Optional<UserProfile> findByUsername(String username);
}
