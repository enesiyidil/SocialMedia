package org.socialmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.socialmedia.entity.enums.EStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileResponseDto {

    private Long id;

    private Long authId;

    private String username;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

    private EStatus status;
}
