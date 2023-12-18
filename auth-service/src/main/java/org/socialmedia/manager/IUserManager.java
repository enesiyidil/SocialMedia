package org.socialmedia.manager;

import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-userprofile", url = "http://localhost:9091/user", decode404 = true)
public interface IUserManager {

    @PostMapping("/save")
    ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto);

    @PostMapping("/activestatus")
    public ResponseEntity<Boolean> activeStatus(@RequestBody ActiveStatusRequestDto dto);

}
