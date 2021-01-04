package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.domain.User;
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
public class TweetRepositoryTests {

  @Autowired TweetRepository tweetRepository;
  @Autowired HashtagRepository hashtagRepository;
  @Autowired UserRepository userRepository;

  @Before
  public void start(){
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    if(!resultUser.isPresent()){
      User user = userRepository.save(new User(null, "saveuser1@gmail.com", "saveuser1", null));
      hashtagRepository.save(new Hashtag(null, "#TesteMagrathea", user, null));
    }
  }

  @Test
  public void saveTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagRepository.findAllByOwnerId(user.getId());
    Hashtag hashtag = hashtags.get(0);
    Tweet createdTweet = tweetRepository.save(
      new Tweet(
          null,
      1L,
    "Hello World!",
      "Jean",
        "1609252487",
  "https://pbs.twimg.com/profile_images/958754881153699840/7SXwADp7_normal.jpg",
              hashtag
      )
    );
    assertThat(createdTweet.getMessage()).isEqualTo("Hello World!");
  }

  @Test
  public void findLastByHashtagIdTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagRepository.findAllByOwnerId(user.getId());
    Hashtag hashtag = hashtags.get(0);
    Optional<Tweet> result = tweetRepository.findTopByHashtagIdOrderByIdDesc(hashtag.getId());
    Tweet tweet = result.get();
    assertThat(tweet.getAuthor()).isEqualTo("Jean");
  }

  @Test
  public void findAllByHashtagIdTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagRepository.findAllByOwnerId(user.getId());
    Hashtag hashtag = hashtags.get(0);
    List<Tweet> tweets = tweetRepository.findAllByHashtagIdOrderByIdDesc(hashtag.getId());
    assertThat(tweets.size()).isGreaterThan(0);
  }
}
