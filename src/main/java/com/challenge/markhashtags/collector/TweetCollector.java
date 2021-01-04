package com.challenge.markhashtags.collector;

import com.challenge.markhashtags.auth.TwitterAuthentication;
import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.InternalServerErrorException;
import com.challenge.markhashtags.service.HashtagService;
import com.challenge.markhashtags.service.TweetService;
import com.challenge.markhashtags.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class TweetCollector {

  private final UserService userService;
  private final HashtagService hashtagService;
  private final TweetService tweetService;
  private final TwitterAuthentication twitterAuthentication;

  //Every 10 minutes
  @Scheduled(cron = "0 0/10 * * * *")
  public void collectPeriodicTweets() {
    List<User> users = userService.getAll();
    DateFormat dateFormat = new SimpleDateFormat("E, MMMM dd, yyyy hh:mm:ss a");
    for (User user : users) {
      List<Hashtag> hashtags = hashtagService.getAllByOwnerId(user.getId());
      for (Hashtag hashtag : hashtags) {
        Tweet lastTweet = tweetService.getLastByHashtagId(hashtag.getId());
        List<Status> tweets = collectTweets(hashtag.getTitle(), lastTweet);
        Collections.reverse(tweets);
        for (Status tweet : tweets) {
          tweetService.save(
              new Tweet(
                  null,
                  tweet.getId(),
                  tweet.getText(),
                  tweet.getUser().getName(),
                  dateFormat.format(tweet.getCreatedAt()),
                  tweet.getUser().get400x400ProfileImageURLHttps(),
                  hashtag));
        }
      }
    }
  }

  public List<Status> collectTweets(String title, Tweet lastTweet) {
    try {
      Query query = new Query();
      query.setCount(100);
      query.setQuery(title);
      if (lastTweet != null) query.setSinceId(lastTweet.getTweetId());
      Twitter twitter = twitterAuthentication.requestAuthenticatedTwitter();
      QueryResult results = twitter.search(query);
      return results.getTweets();
    } catch (TwitterException ex) {
      throw new InternalServerErrorException("Can't communicate with the Twitter API");
    }
  }

  public void collectFirstTweets(Hashtag hashtag) {
    DateFormat dateFormat = new SimpleDateFormat("E, MMMM dd, yyyy hh:mm:ss a");
    List<Status> tweets = collectTweets(hashtag.getTitle(), null);
    Collections.reverse(tweets);
    for (Status tweet : tweets) {
      tweetService.save(
          new Tweet(
              null,
              tweet.getId(),
              tweet.getText(),
              tweet.getUser().getScreenName(),
              dateFormat.format(tweet.getCreatedAt()),
              tweet.getUser().get400x400ProfileImageURLHttps(),
              hashtag));
    }
  }
}
