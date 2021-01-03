package com.challenge.markhashtags.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.Tweet;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.specific.HashtagNotFoundException;
import com.challenge.markhashtags.service.HashtagService;
import com.challenge.markhashtags.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@AutoConfigureMockMvc
@SpringBootTest
public class HashtagResourceTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserService userService;
    @Autowired private HashtagService hashtagService;

    @Test
    public void saveTest() throws Exception {
        String hashtagString = "{\"title\":\"#DesafioMagrathea\", \"owner\":{\"id\":1, \"email\":\"jean@gmail.com\", \"username\":\"jeanklose\"}}";
        MvcResult result = this.mockMvc.perform(post("/hashtag")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(hashtagString)
                )
                .andExpect(status().isCreated())
                .andReturn();
        hashtagString = result.getResponse().getContentAsString();
        Hashtag hashtag = new ObjectMapper().readValue(hashtagString, Hashtag.class);
        assertThat(hashtag.getTitle()).isEqualTo("#DesafioMagrathea");
    }

    @Test
    public void getByIdTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hashtag/1"))
                .andExpect(status().isOk())
                .andReturn();
        String hashtagString = result.getResponse().getContentAsString();
        Hashtag hashtag = new ObjectMapper().readValue(hashtagString, Hashtag.class);
        assertThat(hashtag.getTitle()).isEqualTo("#SAOxGRE");
    }

    @Test
    public void getAllByOwnerIdTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hashtag/owner/1"))
                .andExpect(status().isOk())
                .andReturn();
        String hashtagsString = result.getResponse().getContentAsString();
        List<Hashtag> hashtags = new ObjectMapper().readValue(hashtagsString, new TypeReference<List<Hashtag>>() {});
        assertThat(hashtags.size()).isGreaterThan(0);
    }

    @Test
    public void getAllTweetsByHashtagIdTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hashtag/1/tweets"))
                .andExpect(status().isOk())
                .andReturn();
        String tweetsString = result.getResponse().getContentAsString();
        List<Tweet> tweets = new ObjectMapper().readValue(tweetsString, new TypeReference<List<Tweet>>() {});
        assertThat(tweets.size()).isGreaterThan(0);
    }

    @Test(expected = HashtagNotFoundException.class)
    public void deleteTest() throws Exception {
        User user = userService.getById(1L);
        Hashtag hashtag = hashtagService.save(new Hashtag(null, "#TesteMockDelete", user, null));
        this.mockMvc.perform(delete("/hashtag/"+hashtag.getId()))
                .andExpect(status().isOk())
                .andReturn();
        hashtagService.getById(hashtag.getId());
    }

}
