package com.challenge.markhashtags.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.markhashtags.auth.dto.RequestTokenResponseDTO;
import com.challenge.markhashtags.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@AutoConfigureMockMvc
@SpringBootTest
public class UserResourceTests {

    @Autowired private MockMvc mockMvc;

    @Test
    public void getByIdTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andReturn();
        String userString = result.getResponse().getContentAsString();
        User user = new ObjectMapper().readValue(userString, User.class);
        assertThat(user.getUsername()).isEqualTo("jeanklose");
    }

    @Test
    public void loginTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andReturn();
        String tokenString = result.getResponse().getContentAsString();
        RequestTokenResponseDTO tokenDTO = new ObjectMapper().readValue(tokenString, RequestTokenResponseDTO.class);
        assertThat(tokenDTO.getAuthorizationUrl()).startsWith("https://api.twitter.com/");
    }

}
