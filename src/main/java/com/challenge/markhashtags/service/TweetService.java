package com.challenge.markhashtags.service;

import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TweetService {

  private TweetRepository tweetRepository;

  public Tweet save(Tweet tweet) {
    return tweetRepository.save(tweet);
  }

  public List<Tweet> getAllByHashtagId(Long id) {
    return tweetRepository.findAllByHashtagIdOrderByIdDesc(id);
  }

  public Tweet getLastByHashtagId(Long id) {
    return tweetRepository.findTopByHashtagIdOrderByIdDesc(id).orElse(null);
  }
}
