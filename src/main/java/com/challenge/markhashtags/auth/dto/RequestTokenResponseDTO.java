package com.challenge.markhashtags.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestTokenResponseDTO {

    private String oauth_token;
    private String oauth_tokenSecret;
    private String authorizationUrl;

}
