package iam.sde.udemyblog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Email(message = "Enter a valid email")
    private String emailId;
    @Size(min = 20, message = "Min 20 characters are required")
    private String messageBody;
}
