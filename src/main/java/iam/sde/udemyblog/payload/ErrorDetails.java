package iam.sde.udemyblog.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String description;
}
