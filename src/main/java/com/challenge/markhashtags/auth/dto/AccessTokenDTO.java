package com.challenge.markhashtags.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccessTokenDTO {

    @NotNull
    @NotBlank
    private String oauth_token;

    @NotNull
    @NotBlank
    private String oauth_tokenSecret;

    @NotNull
    @NotBlank
    private String oauth_verifier;

}
