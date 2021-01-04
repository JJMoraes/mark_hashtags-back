package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
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
public class UserRepositoryTests {

  @Autowired UserRepository userRepository;

  @Test
  public void saveTest() {
    User createdUser = userRepository.save(new User(null, "saveuser1@gmail.com", "saveuser1", null));
    assertThat(createdUser.getUsername()).isEqualTo("saveuser1");
  }

  @Test
  public void findByIdTest() {
    User createdUser = userRepository.save(new User(null, "saveuser2@gmail.com", "saveuser2", null));
    Optional<User> resultUser = userRepository.findById(createdUser.getId());
    User user = resultUser.get();
    assertThat(user.getUsername()).isEqualTo("saveuser2");
  }

  @Test
  public void findByEmailTest() {
    Optional<User> result = userRepository.findByEmail("saveuser1@gmail.com");
    User user = result.get();
    assertThat(user.getUsername()).isEqualTo("saveuser1");
  }

  @Test
  public void findAllTest() {
    List<User> users = userRepository.findAll();
    assertThat(users.size()).isGreaterThan(0);
  }

  @Test
  public void deleteTest() {
    User newUser = new User(null, "userdelete@gmail.com", "userdelete", null);
    User createdUser = userRepository.save(newUser);
    userRepository.delete(createdUser);
    Optional<User> user = userRepository.findById(createdUser.getId());
    assertThat(user.isPresent()).isFalse();
  }
}
