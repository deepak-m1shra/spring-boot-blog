package iam.sde.udemyblog.service.impl;

import iam.sde.udemyblog.entity.Comment;
import iam.sde.udemyblog.entity.Post;
import iam.sde.udemyblog.exception.BlogAPIException;
import iam.sde.udemyblog.exception.ResourceNotFoundException;
import iam.sde.udemyblog.payload.CommentDto;
import iam.sde.udemyblog.repository.CommentRepository;
import iam.sde.udemyblog.repository.PostRepository;
import iam.sde.udemyblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        //TODO: When I fetched info using postrepository, I got StackOverflow error [INVESTIGATE]
        // However, when I created a new method in CommentRepository, it worked just fine (why?)
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Long postId, CommentDto commentDto) {
        // validate post Id
        Comment comment = mapToEntity(commentDto);
        if (validatePostExists(postId)) {
            var post = postRepository.findById(postId).get();
            comment.setPost(post);
            Comment newComment = commentRepository.save(comment);
        } else throw new ResourceNotFoundException("Post", "postId", postId);
        return mapToDto(comment);
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (postId != comment.getPost().getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to given post");
        }

        return mapToDto(comment);
    }

    private boolean validatePostExists(Long postId) {
        return postRepository.findById(postId).isPresent();
    }

    @Override
    public CommentDto addComment(Long postId) {
        return null;
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        Post post = getPost(postId);
        Comment comment = getComment(commentId);

        if (comment.getPost().getId() != postId) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "postId doesn't belong to given commentId");
        }

        comment.setMessageBody(commentDto.getMessageBody());
        comment.setName(commentDto.getName());
        comment.setEmailId(commentDto.getEmailId());

        return mapToDto(commentRepository.save(comment));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = getPost(postId);
        Comment comment = getComment(commentId);

        if (validatePostComment(post, comment)) {
            commentRepository.delete(comment);
        } else {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Doesn't belong here");
        }
    }

    private boolean validatePostComment(Post post, Comment comment) {

        return comment.getPost().getId().equals(post.getId());
    }

    @Override
    public void deleteComment(Long postId) {

    }

    @Override
    public void deleteAllComments(Long postId) {

    }

    private CommentDto mapToDto(Comment comment) {

        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }
}
