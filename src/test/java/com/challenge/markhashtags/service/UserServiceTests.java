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
    User createdUser = userService.save(new User(null, "saveuser3@gmail.com", "saveuser3", null));
    assertThat(createdUser.getUsername()).isEqualTo("saveuser3");
  }

  @Test
  public void getByIdTest() {
    User createdUser = userService.save(new User(null, "saveuser4@gmail.com", "saveuser4", null));
    User user = userService.getById(createdUser.getId());
    assertThat(user.getUsername()).isEqualTo("saveuser4");
  }

  @Test
  public void getByEmailTest() {
    Optional<User> resultUser = userService.getByEmail("saveuser1@gmail.com");
    User user = resultUser.get();
    assertThat(user.getUsername()).isEqualTo("saveuser1");
  }

  @Test
  public void getByEmailErrorTest() {
    Optional<User> result = userService.getByEmail("testeErro@gmail.com");
    assertThat(result.isPresent()).isFalse();
  }

  @Test(expected = UserNotFoundException.class)
  public void getByIdErrorTest() {
    userService.getById(111111111L);
  }

  @Test
  public void loginTest() {
    assertThat(userService.login()).isInstanceOf(RequestTokenResponseDTO.class);
  }
}
