package org.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateRequestDto {

    @NotBlank
    private String token;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;
}
