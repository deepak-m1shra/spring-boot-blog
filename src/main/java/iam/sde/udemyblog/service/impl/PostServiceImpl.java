package iam.sde.udemyblog.service.impl;

import iam.sde.udemyblog.entity.Post;
import iam.sde.udemyblog.exception.ResourceNotFoundException;
import iam.sde.udemyblog.payload.PostDto;
import iam.sde.udemyblog.payload.PostResponse;
import iam.sde.udemyblog.repository.PostRepository;
import iam.sde.udemyblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Need of mapper
        // Map postDto to post

        Post post = mapToEntity(postDto);

        Post savedResponse = postRepository.save(post);

        PostDto responseDto = mapToDto(savedResponse);

        return responseDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        PostDto dbDto = getById(id);
        Post post = new Post();

        post.setPostedBy(postDto.getPostedBy());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setId(id);

        Post updatePost = postRepository.save(post);

        return mapToDto(updatePost);
    }

    private PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String order) {

        Sort sort = order.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postsDto = posts.stream().map(this::mapToDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setContent(postsDto);
        postResponse.setLast(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "getById", id));
        return mapToDto(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
