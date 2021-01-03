package com.challenge.markhashtags.auth;

import com.challenge.markhashtags.exception.InternalServerErrorException;
import com.challenge.markhashtags.exception.specific.BadCredentialsException;
import com.challenge.markhashtags.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

@AllArgsConstructor
@Service
public class TwitterAuthentication {

  private final UserRepository userRepository;
  private final Environment env;

  public Twitter requestTwitter() {
    ConfigurationBuilder builder = new ConfigurationBuilder();
    builder
        .setDebugEnabled(true)
        .setOAuthConsumerKey(env.getProperty("twitter.consumer.key"))
        .setOAuthConsumerSecret(env.getProperty("twitter.consumer.secret"))
        .setIncludeEmailEnabled(true);

    TwitterFactory factory = new TwitterFactory(builder.build());
    Twitter twitter = factory.getInstance();
    return twitter;
  }

  public RequestToken requestAuthorizationToken() {
    try {
      Twitter twitter = requestTwitter();
      return twitter.getOAuthRequestToken();
    } catch (TwitterException ex) {
      throw new InternalServerErrorException("Can't communicate with Twitter API");
    }
  }

  public User requestUser(String oauth_token, String oauth_tokenSecret, String oauth_verifier) {
    try {
      Twitter twitter = requestTwitter();
      RequestToken request = new RequestToken(oauth_token, oauth_tokenSecret);
      AccessToken accessToken = twitter.getOAuthAccessToken(request, oauth_verifier);
      twitter.setOAuthAccessToken(accessToken);
      User user = twitter.verifyCredentials();
      return user;
    } catch (TwitterException ex) {
      throw new BadCredentialsException();
    }
  }

  public Twitter requestAuthenticatedTwitter() {
    ConfigurationBuilder builder = new ConfigurationBuilder();
    builder
        .setDebugEnabled(true)
        .setOAuthConsumerKey(env.getProperty("twitter.consumer.key"))
        .setOAuthConsumerSecret(env.getProperty("twitter.consumer.secret"))
        .setOAuthAccessToken(env.getProperty("twitter.access"))
        .setOAuthAccessTokenSecret(env.getProperty("twitter.access.secret"));
    TwitterFactory factory = new TwitterFactory(builder.build());
    Twitter twitter = factory.getInstance();
    return twitter;
  }
}
