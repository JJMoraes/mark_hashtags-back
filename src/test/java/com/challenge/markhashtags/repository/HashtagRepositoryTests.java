package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Hashtag;
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
public class HashtagRepositoryTests {

  @Autowired HashtagRepository hashtagRepository;
  @Autowired UserRepository userRepository;

  @Before
  public void startUser(){
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    if(!resultUser.isPresent())
      userRepository.save(new User(null, "saveuser1@gmail.com", "saveuser1", null));
  }

  @Test
  public void saveTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    Hashtag createdHashtag = hashtagRepository.save(new Hashtag(null, "#TesteMagrathea", user, null));
    assertThat(createdHashtag.getTitle()).isEqualTo("#TesteMagrathea");
  }

  @Test
  public void findByIdTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    Hashtag createdHashtag = hashtagRepository.save(new Hashtag(null, "#TesteMagrathea2", user, null));
    Optional<Hashtag> resultHashtag = hashtagRepository.findById(createdHashtag.getId());
    Hashtag hashtag = resultHashtag.get();
    assertThat(hashtag.getTitle()).isEqualTo("#TesteMagrathea2");
  }

  @Test
  public void findAllByUserIdTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagRepository.findAllByOwnerId(user.getId());
    assertThat(hashtags.size()).isGreaterThan(0);
  }

  @Test
  public void deleteTest() {
    Optional<User> resultUser = userRepository.findByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    Hashtag createdHashtag = hashtagRepository.save(new Hashtag(null, "#DeleteTeste", user, null));
    hashtagRepository.delete(createdHashtag);
    Optional<Hashtag> deletedHashtag = hashtagRepository.findById(createdHashtag.getId());
    assertThat(deletedHashtag.isPresent()).isFalse();
  }
}
