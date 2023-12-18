package org.socialmedia.service;

import org.socialmedia.dto.response.UserProfileResponseDto;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.mapper.IUserMapper;
import org.socialmedia.repository.UserProfileRepository;
import org.socialmedia.utility.ServiceManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {

    private final UserProfileRepository userProfileRepository;

    private final RestTemplate restTemplate;


    public UserProfileService(UserProfileRepository userProfileRepository, RestTemplate restTemplate) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.restTemplate = restTemplate;
    }

    public void saveAll(){
        ResponseEntity<List<UserProfileResponseDto>> response = restTemplate.exchange(
                "http://localhost:9091/user/findall",
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserProfileResponseDto>>() {});
        List<UserProfile> users = Objects.requireNonNull(response.getBody()).stream().map(IUserMapper.INSTANCE::toUserProfile).collect(Collectors.toList());
        System.out.println(users);
        userProfileRepository.saveAll(users);
    }

}
