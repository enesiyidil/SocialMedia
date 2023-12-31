package org.socialmedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.socialmedia.entity.enums.EStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "user_profile")
public class UserProfile {

    @Id
    private String id;

//    private String userProfileId;

    private Long authId;

    private String username;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

    @Builder.Default
    private EStatus status = EStatus.PENDING;
}
