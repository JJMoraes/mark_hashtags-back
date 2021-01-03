package com.challenge.markhashtags.service;

import com.challenge.markhashtags.auth.dto.RequestTokenResponseDTO;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.specific.UserNotFoundException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class UserServiceTests {

  @Autowired UserService userService;

  @Test
  public void saveTest() {
    User newUser = new User(null, "joaquim@gmail.com", "joaquim", null);
    User createdUser = userService.save(newUser);
    assertThat(createdUser.getUsername()).isEqualTo("joaquim");
  }

  @Test
  public void getByIdTest() {
    User user = userService.getById(1L);
    assertThat(user.getUsername()).isEqualTo("jeanklose");
  }

  @Test
  public void getByEmailTest() {
    Optional<User> result = userService.getByEmail("jean@gmail.com");
    User user = result.get();
    assertThat(user.getUsername()).isEqualTo("jeanklose");
  }

  @Test
  public void getByEmailErrorTest() {
    Optional<User> result = userService.getByEmail("testeErro@gmail.com");
    assertThat(result.isPresent()).isFalse();
  }

  @Test(expected = UserNotFoundException.class)
  public void getByIdErrorTest() {
    userService.getById(2L);
  }

  @Test
  public void loginTest() {
    assertThat(userService.login()).isInstanceOf(RequestTokenResponseDTO.class);
  }
}
