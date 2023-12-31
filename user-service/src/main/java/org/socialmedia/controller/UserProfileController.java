package org.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.dto.request.UpdateRequestDto;
import org.socialmedia.dto.request.UserSaveRequestDto;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.service.UserProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @PostMapping("/save")
    public ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.createNewUser(dto));
    }

    @PostMapping("/activestatus")
    public ResponseEntity<Boolean> activeStatus(@RequestBody ActiveStatusRequestDto dto) {
        return ResponseEntity.ok(service.activeStatus(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> softDelete(@PathVariable Long id) {
        return ResponseEntity.ok(service.softDelete(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateRequestDto dto) {
        return ResponseEntity.ok(service.updateProfile(dto));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<UserProfile>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/getUserIdfromToken/{token}")
    public String getUserIdfromToken(@PathVariable String token) {
        return service.getUserIdfromToken(token);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.findByUsername(username));
    }

    @GetMapping("/findallbypageable")
    public ResponseEntity<Page<UserProfile>> findAllByPageable(Integer pageSize, Integer pageNumber,
                                                               @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                               @RequestParam(required = false, defaultValue = "id") String sortParameter) {
        return ResponseEntity.ok(service.findAllByPageable(pageSize, pageNumber, direction, sortParameter));
    }

    @GetMapping("/findallbyslice")
    public ResponseEntity<Slice<UserProfile>> findAllBySlice(Integer pageSize, Integer pageNumber,
                                                             @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                             @RequestParam(required = false, defaultValue = "id") String sortParameter) {
        return ResponseEntity.ok(service.findAllBySlice(pageSize, pageNumber, direction, sortParameter));
    }
}
