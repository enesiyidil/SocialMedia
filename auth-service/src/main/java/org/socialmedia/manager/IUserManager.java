package org.socialmedia.manager;

import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-userprofile", url = "${feign.user}", decode404 = true)
public interface IUserManager {

    @PostMapping("/save")
    ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto, @RequestHeader("Authorization") String token);

    @PostMapping("/activestatus")
    public ResponseEntity<Boolean> activeStatus(@RequestBody ActiveStatusRequestDto dto);

}
