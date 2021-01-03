package com.challenge.markhashtags.service;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.especific.HashtagNotFoundException;
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
public class HashtagServiceTests {

    @Autowired HashtagService hashtagService;
    @Autowired UserService userService;

    @Test
    public void saveTest(){
        User user = userService.getById(1L);
        Hashtag newHashtag = new Hashtag(null, "#TesteMagrathea", user, null);
        Hashtag hashtag = hashtagService.save(newHashtag);
        assertThat(hashtag.getTitle()).isEqualTo("#TesteMagrathea");
    }

    @Test
    public void getByIdTest(){
        Hashtag hashtag = hashtagService.getById(1L);
        assertThat(hashtag.getTitle()).isEqualTo("#SAOxGRE");
    }

    @Test
    public void getAllByUserIdTest(){
        List<Hashtag> hashtags = hashtagService.getAllByOwnerId(1L);
        assertThat(hashtags.size()).isGreaterThan(0);
    }

    @Test(expected = HashtagNotFoundException.class)
    public void deleteTest(){
        User user = userService.getById(1L);
        Hashtag newHashtag = new Hashtag(null, "#DeleteTeste", user, null);
        Hashtag hashtag = hashtagService.save(newHashtag);
        hashtagService.delete(hashtag.getId());
        hashtagService.getById(hashtag.getId());
    }

}
