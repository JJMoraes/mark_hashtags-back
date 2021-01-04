package com.challenge.markhashtags.resource;

import com.challenge.markhashtags.auth.dto.RequestTokenResponseDTO;
import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.repository.UserRepository;
import com.challenge.markhashtags.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@FixMethodOrder
@AutoConfigureMockMvc
@SpringBootTest
public class UserResourceTests {

  @Autowired private MockMvc mockMvc;
  @Autowired private UserService userService;

  @Before
  public void start(){
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    if(!resultUser.isPresent())
      userService.save(new User(null, "usersave1@gmail.com", "usersave1", null));
  }

  @Test
  public void getByIdTest() throws Exception {
    Optional<User> resultUser = userService.getByEmail("usersave1@gmail.com");
    User user = resultUser.get();
    MvcResult result = this.mockMvc.perform(get("/user/"+user.getId())).andExpect(status().isOk()).andReturn();
    String userString = result.getResponse().getContentAsString();
    User searchUser = new ObjectMapper().readValue(userString, User.class);
    assertThat(searchUser.getUsername()).isEqualTo("usersave1");
  }

  @Test
  public void loginTest() throws Exception {
    MvcResult result =
        this.mockMvc.perform(get("/user/login")).andExpect(status().isOk()).andReturn();
    String tokenString = result.getResponse().getContentAsString();
    RequestTokenResponseDTO tokenDTO =
        new ObjectMapper().readValue(tokenString, RequestTokenResponseDTO.class);
    assertThat(tokenDTO.getAuthorizationUrl()).startsWith("https://api.twitter.com/");
  }
}
