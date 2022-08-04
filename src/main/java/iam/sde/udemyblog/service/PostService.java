package iam.sde.udemyblog.service;

import iam.sde.udemyblog.payload.PostDto;
import iam.sde.udemyblog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto updatePost(PostDto postDto, Long id);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String order);
    PostDto getById(Long id);
    void deletePost(Long id);
}
