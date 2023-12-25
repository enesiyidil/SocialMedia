package org.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.dto.request.LoginRequestDto;
import org.socialmedia.dto.request.RegisterRequestDto;
import org.socialmedia.dto.request.UpdateRequestDto;
import org.socialmedia.dto.response.LoginResponseDto;
import org.socialmedia.dto.response.RegisterResponseDto;
import org.socialmedia.entity.Auth;
import org.socialmedia.exception.AuthServiceException;
import org.socialmedia.exception.ErrorType;
import org.socialmedia.service.AuthService;
import org.socialmedia.utility.JWTTokenManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JWTTokenManager jwtTokenManager;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto request){
        if(!request.getPassword().equals(request.getRePassword())){
            throw new AuthServiceException(ErrorType.PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/activestatus")
    public ResponseEntity<String> activeStatus(@RequestBody ActiveStatusRequestDto dto){
        return ResponseEntity.ok(authService.activeStatus(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id){
        return ResponseEntity.ok(authService.softDelete(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmail(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(authService.updateEmail(dto));
    }

    @GetMapping("/gettoken")
    public ResponseEntity<String> getToken(Long id){
        return ResponseEntity.ok(jwtTokenManager.createToken(id).get());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/findall")
    public ResponseEntity<List<Auth>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }
}
