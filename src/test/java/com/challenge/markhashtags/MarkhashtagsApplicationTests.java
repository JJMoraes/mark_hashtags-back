package com.challenge.markhashtags;

import com.challenge.markhashtags.repository.HashtagRepositoryTests;
import com.challenge.markhashtags.repository.TweetRepositoryTests;
import com.challenge.markhashtags.repository.UserRepositoryTests;
import com.challenge.markhashtags.resource.HashtagResourceTests;
import com.challenge.markhashtags.resource.UserResourceTests;
import com.challenge.markhashtags.service.HashtagServiceTests;
import com.challenge.markhashtags.service.TweetServiceTests;
import com.challenge.markhashtags.service.UserServiceTests;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarkhashtagsApplicationTests {

  @Test
  public void contextLoads() {

    JUnitCore junit = new JUnitCore();
    junit.addListener(new TextListener(System.out));

    //Certify that you change ddl-auto property to create
    junit.run(UserRepositoryTests.class);
    junit.run(HashtagRepositoryTests.class);
    junit.run(TweetRepositoryTests.class);

    junit.run(UserServiceTests.class);
    junit.run(HashtagServiceTests.class);
    junit.run(TweetServiceTests.class);

    //Certify that you disable JWT filter
    junit.run(UserResourceTests.class);
    junit.run(HashtagResourceTests.class);

  }
}
