package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
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
public class HashtagRepositoryTests {

  @Autowired HashtagRepository hashtagRepository;
  @Autowired UserRepository userRepository;

  @Test
  public void saveTest() {
    Optional<User> result = userRepository.findById(1L);
    User user = result.get();
    Hashtag newHashtag = new Hashtag(null, "#SAOxGRE", user, null);
    Hashtag hashtag = hashtagRepository.save(newHashtag);
    assertThat(hashtag.getTitle()).isEqualTo("#SAOxGRE");
  }

  @Test
  public void findByIdTest() {
    Optional<Hashtag> result = hashtagRepository.findById(1L);
    Hashtag hashtag = result.get();
    assertThat(hashtag.getTitle()).isEqualTo("#SAOxGRE");
  }

  @Test
  public void findAllByUserIdTest() {
    List<Hashtag> hashtags = hashtagRepository.findAllByOwnerId(1L);
    assertThat(hashtags.size()).isGreaterThan(0);
  }

  @Test
  public void deleteTest() {
    Optional<User> result = userRepository.findById(1L);
    User user = result.get();
    Hashtag newHashtag = new Hashtag(null, "#DeleteTeste", user, null);
    Hashtag hashtag = hashtagRepository.save(newHashtag);
    hashtagRepository.delete(hashtag);
    Optional<Hashtag> deletedHashtag = hashtagRepository.findById(hashtag.getId());
    assertThat(deletedHashtag.isPresent()).isFalse();
  }
}
