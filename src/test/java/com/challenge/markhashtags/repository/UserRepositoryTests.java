package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.User;
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
    User newUser = new User(null, "jean@gmail.com", "jeanklose", null);
    User createdUser = userRepository.save(newUser);
    assertThat(createdUser.getUsername()).isEqualTo("jeanklose");
  }

  @Test
  public void findByIdTest() {
    Optional<User> result = userRepository.findById(1L);
    User user = result.get();
    assertThat(user.getUsername()).isEqualTo("jeanklose");
  }

  @Test
  public void findByEmailTest() {
    Optional<User> result = userRepository.findByEmail("jean@gmail.com");
    User user = result.get();
    assertThat(user.getUsername()).isEqualTo("jeanklose");
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
