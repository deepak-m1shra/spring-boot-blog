package iam.sde.udemyblog.payload;


import lombok.Data;

@Data
public class JwtAuthResponse {

    private final String accessToken;
    private String tokenType = "Bearer";
}
