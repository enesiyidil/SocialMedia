package org.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.socialmedia.dto.request.PostSaveRequestDto;
import org.socialmedia.entity.Post;
import org.socialmedia.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/save")
    public ResponseEntity<Post> createPost(@RequestBody PostSaveRequestDto request){

        return ResponseEntity.ok(postService.createPost(request));
    }

}