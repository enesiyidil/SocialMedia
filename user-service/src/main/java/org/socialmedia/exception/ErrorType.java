package org.socialmedia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),

    EMAIL_EXITS(2100, "Email Kullanılıyor", HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(5000, "Parametre hatası", HttpStatus.BAD_REQUEST),

    PASSWORD_MISMATCH(2200, "Şifreler Uyuşmuyor", HttpStatus.BAD_REQUEST),

    EMAIL_OR_PASSWORD_NOT_VALID(3000, "Email veya şifre hatalı", HttpStatus.BAD_REQUEST),

    USER_NOT_ACTIVE(3001, "Kullanıcı aktif değil", HttpStatus.FORBIDDEN),

    USER_NOT_FOUND(3002, "Kullanıcı bulunamadı", HttpStatus.NOT_FOUND),

    STATUS_NOT_SUITABLE(3003, "Kullanıcı durumu ", HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(6000,"Geçersiz token",HttpStatus.BAD_REQUEST),

    TOKEN_NOT_CREATE(6001,"Token oluşturulmadı",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus httpStatus;
}
