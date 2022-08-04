package iam.sde.udemyblog.controller;

import iam.sde.udemyblog.payload.PostDto;
import iam.sde.udemyblog.payload.PostResponse;
import iam.sde.udemyblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto) {

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts() {
//        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "asc", required = false) String order) {
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {

        PostDto updatePostResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatePostResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(String.format("Post with id %s deleted successfully!", id), HttpStatus.OK);
    }
}
