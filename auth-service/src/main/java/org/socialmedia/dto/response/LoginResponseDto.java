package org.socialmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.socialmedia.entity.enums.ERole;
import org.socialmedia.entity.enums.EStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Long id;

    private String username;

    private String email;

    private ERole role;

    private EStatus status;
}
