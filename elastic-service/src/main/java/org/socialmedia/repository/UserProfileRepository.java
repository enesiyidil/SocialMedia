package org.socialmedia.repository;

import org.socialmedia.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends ElasticsearchRepository<UserProfile, String> {
}
