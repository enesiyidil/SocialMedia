package org.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotNull
    @NotBlank(message = "Kullanıcı adı boş geçilmez")
    private String email;

    @NotNull
    @NotBlank(message = "Şifre boş geçilmez")
    private String password;
}
