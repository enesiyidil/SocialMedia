package org.socialmedia.repository;

import jdk.dynalink.linker.LinkerServices;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.entity.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, Long> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);

    Optional<UserProfile> findByAuthId(Long id);

    Optional<UserProfile> findByUsername(String username);

    @Query("{ 'authId' : { $gt: ?0 } }")
    List<UserProfile> findAllByAuthIdGreaterThan(Long authId);

    @Query("{ $or: [ { 'authId' : { $gt: ?0 } }, { 'status' : ?1 } ] }")
    List<UserProfile> findAllByAuthIdGreaterThanOrStatus(Long authId, EStatus eStatus);


    // @Query("SELECT u FROM UserProfile u WHERE u.status = ACTIVE")
    // @Query("SELECT u FROM UserProfile u WHERE u.authId > :authId")
    // @Query("SELECT u FROM UserProfile u WHERE u.authId > :authId OR u.status = :status")
}
