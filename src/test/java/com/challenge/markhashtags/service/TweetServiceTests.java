package com.challenge.markhashtags.service;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.repository.HashtagRepository;
import com.challenge.markhashtags.repository.UserRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class TweetServiceTests {

  @Autowired TweetService tweetService;
  @Autowired UserService userService;
  @Autowired HashtagService hashtagService;

  @Before
  public void start(){
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    if(!resultUser.isPresent()){
      User user = userService.save(new User(null, "usersave1@gmail.com", "usersave1", null));
      hashtagService.save(new Hashtag(null, "#TesteMagrathea", user, null));
    }
  }

  @Test
  public void saveTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagService.getAllByOwnerId(user.getId());
    Hashtag hashtag = hashtags.get(0);
    Tweet tweet = tweetService.save(
      new Tweet(
      null,
      2L,
      "Hello World2!",
      "Jean",
      "1609252487",
      "https://pbs.twimg.com/profile_images/958754881153699840/7SXwADp7_normal.jpg",
      hashtag
      )
    );
    assertThat(tweet.getMessage()).isEqualTo("Hello World2!");
  }

  @Test
  public void getAllByHashtagIdTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagService.getAllByOwnerId(user.getId());
    Hashtag hashtag = hashtags.get(0);
    List<Tweet> tweets = tweetService.getAllByHashtagId(hashtag.getId());
    assertThat(tweets.size()).isGreaterThan(0);
  }

  @Test
  public void getLastByHashtagIdTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagService.getAllByOwnerId(user.getId());
    Hashtag hashtag = hashtags.get(0);
    Tweet tweet = tweetService.getLastByHashtagId(hashtag.getId());
    assertThat(tweet.getMessage()).isEqualTo("Hello World2!");
  }
}
