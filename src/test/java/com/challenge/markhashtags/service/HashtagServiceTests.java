package com.challenge.markhashtags.service;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.specific.HashtagNotFoundException;
import org.junit.Before;
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
public class HashtagServiceTests {

  @Autowired HashtagService hashtagService;
  @Autowired UserService userService;

  @Before
  public void start(){
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    if(!resultUser.isPresent())
      userService.save(new User(null, "usersave1@gmail.com", "usersave1", null));
  }

  @Test
  public void saveTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag createdHashtag = hashtagService.save(new Hashtag(null, "#TesteMagrathea2", user, null));
    assertThat(createdHashtag.getTitle()).isEqualTo("#TesteMagrathea2");
  }

  @Test
  public void getByIdTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag createdHashtag = hashtagService.save(new Hashtag(null, "#TesteMagrathea3", user, null));
    Hashtag hashtag = hashtagService.getById(createdHashtag.getId());
    assertThat(hashtag.getTitle()).isEqualTo("#TesteMagrathea3");
  }

  @Test
  public void getAllByUserIdTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    List<Hashtag> hashtags = hashtagService.getAllByOwnerId(user.getId());
    assertThat(hashtags.size()).isGreaterThan(0);
  }

  @Test(expected = HashtagNotFoundException.class)
  public void deleteTest() {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag createdHashtag = hashtagService.save(new Hashtag(null, "#TesteMagrathea3", user, null));
    hashtagService.delete(createdHashtag.getId());
    hashtagService.getById(createdHashtag.getId());
  }
}
