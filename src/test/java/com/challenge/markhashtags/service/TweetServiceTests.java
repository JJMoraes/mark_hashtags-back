package com.challenge.markhashtags.service;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class TweetServiceTests {

  @Autowired TweetService tweetService;
  @Autowired HashtagService hashtagService;

  @Test
  public void saveTest() {
    Hashtag hashtag = hashtagService.getById(1L);
    Tweet newTweet =
        new Tweet(
            1L,
            2L,
            "Hello World!",
            "Jean",
            "1609252487",
            "https://pbs.twimg.com/profile_images/958754881153699840/7SXwADp7_normal.jpg",
            hashtag);
    Tweet tweet = tweetService.save(newTweet);
    assertThat(tweet.getMessage()).isEqualTo("Hello World!");
  }

  @Test
  public void getAllByHashtagIdTest() {
    List<Tweet> tweets = tweetService.getAllByHashtagId(1L);
    assertThat(tweets.size()).isGreaterThan(0);
  }

  @Test
  public void getLastByHashtagIdTest() {
    Tweet tweet = tweetService.getLastByHashtagId(1L);
    assertThat(tweet).isNotNull();
  }
}
