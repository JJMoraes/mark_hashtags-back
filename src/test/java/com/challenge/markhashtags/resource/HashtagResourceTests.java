package com.challenge.markhashtags.resource;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.specific.HashtagNotFoundException;
import com.challenge.markhashtags.service.HashtagService;
import com.challenge.markhashtags.service.TweetService;
import com.challenge.markhashtags.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@FixMethodOrder
@AutoConfigureMockMvc
@SpringBootTest
public class HashtagResourceTests {

  @Autowired private MockMvc mockMvc;
  @Autowired private HashtagService hashtagService;
  @Autowired private UserService userService;
  @Autowired private TweetService tweetService;

  @Before
  public void start(){
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    if(!resultUser.isPresent()){
      User user = userService.save(new User(null, "usersave1@gmail.com", "usersave1", null));
      hashtagService.save(new Hashtag(null, "#TesteMagrathea", user, null));
    }
  }

  @Test
  public void saveTest() throws Exception {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag hashtag = new Hashtag(null, "#TesteMagrathea3", user, null);
    String hashtagString = new ObjectMapper().writeValueAsString(hashtag);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/hashtag").contentType(MediaType.APPLICATION_JSON).content(hashtagString))
            .andExpect(status().isCreated())
            .andReturn();
    hashtagString = result.getResponse().getContentAsString();
    Hashtag searchHashtag = new ObjectMapper().readValue(hashtagString, Hashtag.class);
    assertThat(searchHashtag.getTitle()).isEqualTo("#TesteMagrathea3");
  }

  @Test
  public void getByIdTest() throws Exception {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag hashtag = hashtagService.save(new Hashtag(null, "#TesteMagrathea4", user, null));
    MvcResult result =
        this.mockMvc.perform(get("/hashtag/"+hashtag.getId())).andExpect(status().isOk()).andReturn();
    String hashtagString = result.getResponse().getContentAsString();
    Hashtag searchHashtag = new ObjectMapper().readValue(hashtagString, Hashtag.class);
    assertThat(searchHashtag.getTitle()).isEqualTo("#TesteMagrathea4");
  }

  @Test
  public void getAllByOwnerIdTest() throws Exception {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    MvcResult result =
        this.mockMvc.perform(get("/hashtag/owner/"+user.getId())).andExpect(status().isOk()).andReturn();
    String hashtagsString = result.getResponse().getContentAsString();
    List<Hashtag> hashtags =
        new ObjectMapper().readValue(hashtagsString, new TypeReference<List<Hashtag>>() {});
    assertThat(hashtags.size()).isGreaterThan(0);
  }

  @Test
  public void getAllTweetsByHashtagIdTest() throws Exception {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag hashtag = hashtagService.getAllByOwnerId(user.getId()).get(0);
    tweetService.save(new Tweet(null,2L,"Hello World2!","Jean","1609252487","https://pbs.twimg.com/profile_images/958754881153699840/7SXwADp7_normal.jpg",hashtag));
    MvcResult result =
        this.mockMvc.perform(get("/hashtag/"+hashtag.getId()+"/tweets")).andExpect(status().isOk()).andReturn();
    String tweetsString = result.getResponse().getContentAsString();
    List<Tweet> tweets =
        new ObjectMapper().readValue(tweetsString, new TypeReference<List<Tweet>>() {});
    assertThat(tweets.size()).isGreaterThan(0);
  }

  @Test(expected = HashtagNotFoundException.class)
  public void deleteTest() throws Exception {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    Hashtag hashtag = hashtagService.save(new Hashtag(null, "#TesteMagrathea5", user, null));
    this.mockMvc
        .perform(delete("/hashtag/" + hashtag.getId()))
        .andExpect(status().isOk())
        .andReturn();
    hashtagService.getById(hashtag.getId());
  }
}
