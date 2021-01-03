package com.challenge.markhashtags.resource;

import com.challenge.markhashtags.auth.dto.AccessTokenDTO;
import com.challenge.markhashtags.auth.dto.RequestTokenResponseDTO;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserResource {

  private UserService userService;

  @GetMapping("/login")
  public ResponseEntity<RequestTokenResponseDTO> login() {
    RequestTokenResponseDTO urlLogin = userService.login();
    return ResponseEntity.ok(urlLogin);
  }

  @PostMapping("/sign")
  public ResponseEntity<String> sign(@Valid @RequestBody AccessTokenDTO accessDTO) {
    String string = userService.sign(accessDTO);
    return ResponseEntity.ok(string);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getById(@PathVariable("id") Long id) {
    User user = userService.getById(id);
    return ResponseEntity.ok(user);
  }
}
