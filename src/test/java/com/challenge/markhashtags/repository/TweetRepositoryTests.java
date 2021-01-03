package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import org.junit.FixMethodOrder;
import org.junit.Test;
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

    @Test
    public void saveTest(){
        Optional<Hashtag> result = hashtagRepository.findById(1L);
        Hashtag hashtag = result.get();
        Tweet newTweet = new Tweet(null,1L, "Hello World!", "Jean", "1609252487", "https://pbs.twimg.com/profile_images/958754881153699840/7SXwADp7_normal.jpg", hashtag);
        Tweet tweet = tweetRepository.save(newTweet);
        assertThat(tweet.getMessage()).isEqualTo("Hello World!");
    }

    @Test
    public void findLastByHashtagIdTest() {
        Optional<Tweet> result = tweetRepository.findTopByHashtagIdOrderByIdDesc(1L);
        Tweet tweet = result.get();
        assertThat(tweet.getAuthor()).isEqualTo("Jean");
    }

    @Test
    public void findAllByHashtagIdTest(){
        List<Tweet> tweets = tweetRepository.findAllByHashtagIdOrderByIdDesc(1L);
        assertThat(tweets.size()).isGreaterThan(0);
    }
}
