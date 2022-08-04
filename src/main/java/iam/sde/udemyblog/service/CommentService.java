package iam.sde.udemyblog.service;

import iam.sde.udemyblog.entity.Comment;
import iam.sde.udemyblog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto addComment(Long postId, CommentDto commentDto);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto addComment(Long postId);


    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);


    void deleteComment(Long postId, Long commentId);

    void deleteComment(Long commentId);

    void deleteAllComments(Long postId);
}
