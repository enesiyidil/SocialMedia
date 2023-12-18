package org.socialmedia.service;

import org.socialmedia.dto.request.PostSaveRequestDto;
import org.socialmedia.entity.Post;
import org.socialmedia.manager.IUserManager;
import org.socialmedia.repository.PostRepository;
import org.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post, Long> {

    private final PostRepository repository;

    private final IUserManager userManager;

    public PostService(PostRepository repository, IUserManager userManager) {
        super(repository);
        this.repository = repository;
        this.userManager = userManager;
    }

    public Post createPost(PostSaveRequestDto request) {

        Long id = userManager.getUserIdfromToken(request.getToken());

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getPhoto())
                .userId(id)
                .build();
        return save(post);
    }
}
