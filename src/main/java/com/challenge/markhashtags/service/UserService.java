package com.challenge.markhashtags.service;

import com.challenge.markhashtags.auth.TwitterAuthentication;
import com.challenge.markhashtags.auth.dto.AccessTokenDTO;
import com.challenge.markhashtags.auth.dto.RequestTokenResponseDTO;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.specific.UserNotFoundException;
import com.challenge.markhashtags.repository.UserRepository;
import com.challenge.markhashtags.security.JwtManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.auth.RequestToken;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final TwitterAuthentication twitterAuthentication;
  private final JwtManager jwtManager;

  public User save(User user) {
    return userRepository.save(user);
  }

  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public RequestTokenResponseDTO login() {
    RequestToken requestToken = twitterAuthentication.requestAuthorizationToken();
    return new RequestTokenResponseDTO(
        requestToken.getToken(), requestToken.getTokenSecret(), requestToken.getAuthorizationURL());
  }

  public String sign(AccessTokenDTO accessToken) {
    twitter4j.User twitterUser =
        twitterAuthentication.requestUser(
            accessToken.getOauth_token(),
            accessToken.getOauth_tokenSecret(),
            accessToken.getOauth_verifier());
    Optional<User> result = getByEmail(twitterUser.getEmail());
    User user =
        (result.isPresent())
            ? result.get()
            : save(new User(null, twitterUser.getEmail(), twitterUser.getScreenName(), null));
    String token = jwtManager.createToken(user);
    return token;
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }
}
