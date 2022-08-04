package iam.sde.udemyblog.controller;

import iam.sde.udemyblog.entity.Comment;
import iam.sde.udemyblog.payload.CommentDto;
import iam.sde.udemyblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable(name = "postId") Long postId,
                                                 @Valid @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.addComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(name = "postId") Long postId) {

        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable(name = "postId") Long postId,
                                                 @PathVariable(name = "commentId") Long commentId) {

        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,
                                                @PathVariable(name = "commentId") Long commentId) {

        commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

}
