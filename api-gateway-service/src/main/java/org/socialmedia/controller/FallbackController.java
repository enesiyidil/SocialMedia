package org.socialmedia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuth(){
        /*
         * This is a fallback for the Auth Service.
         * It will be used when the Auth Service is down.
         */
        return ResponseEntity.ok("Auth Service out of service.");
    }

    @GetMapping("/user")
    public ResponseEntity<String> fallbackUser(){
        return ResponseEntity.ok("User Profile Service out of service.");
    }
}
