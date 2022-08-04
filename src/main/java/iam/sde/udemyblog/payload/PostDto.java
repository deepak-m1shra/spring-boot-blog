package iam.sde.udemyblog.payload;

import iam.sde.udemyblog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotEmpty(message = "Title can not be null or empty")
    private String title;
    @NotEmpty
    @Size(min = 5, message = "Minimum length for description field should be 5")
    private String description;
    @NotEmpty(message = "postedBy field can not be empty")
    private String postedBy;
    private Set<CommentDto> comments;
}
