package org.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.socialmedia.constant.ErrorStaticMessage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotNull
    @Size(min = 2, max = 20, message = ErrorStaticMessage.USER_NOT_VALID)
    @NotBlank(message = "Kullanıcı adı boş geçilmez")
    private String username;

    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 20, message = "Şifre uzunluğu 2 - 20 arası olmalıdır.")
    @NotBlank(message = "Şifre boş geçilmez")
    private String password;

    private String rePassword;
}