package com.challenge.markhashtags.resource;

import com.challenge.markhashtags.collector.TweetCollector;
import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.resource.dto.HashtagSaveDTO;
import com.challenge.markhashtags.service.HashtagService;
import com.challenge.markhashtags.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/hashtag")
@RestController
public class HashtagResource {

  private final HashtagService hashtagService;
  private final TweetService tweetService;
  private final TweetCollector tweetCollector;

  @PostMapping()
  public ResponseEntity<Hashtag> save(@Valid @RequestBody HashtagSaveDTO hashtagDTO) {
    Hashtag hashtag = hashtagDTO.toHashtag();
    Hashtag createdHashtag = hashtagService.save(hashtag);
    tweetCollector.collectFirstTweets(createdHashtag);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdHashtag);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Hashtag> getById(@PathVariable("id") Long id) {
    Hashtag hashtag = hashtagService.getById(id);
    return ResponseEntity.ok(hashtag);
  }

  @GetMapping("/owner/{id}")
  public ResponseEntity<List<Hashtag>> getAllByOwnerId(@PathVariable("id") Long id) {
    List<Hashtag> hashtags = hashtagService.getAllByOwnerId(id);
    return ResponseEntity.ok(hashtags);
  }

  @GetMapping("/{id}/tweets")
  public ResponseEntity<List<Tweet>> getAllTweetsByHashtagId(@PathVariable("id") Long id) {
    List<Tweet> tweets = tweetService.getAllByHashtagId(id);
    return ResponseEntity.ok(tweets);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    hashtagService.delete(id);
    return ResponseEntity.ok().build();
  }
}
