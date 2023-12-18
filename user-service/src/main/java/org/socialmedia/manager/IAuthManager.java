package org.socialmedia.manager;

import org.socialmedia.dto.request.UpdateAuthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userprofile-auth", url = "http://localhost:9090/auth", decode404 = true)
public interface IAuthManager {

    @PutMapping("/update")
    ResponseEntity<String> updateEmail(@RequestBody UpdateAuthRequestDto dto);
}
