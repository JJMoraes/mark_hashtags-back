package com.challenge.markhashtags.resource.dto;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HashtagSaveDTO {

  @NotBlank(message = "The title of hashtag can't be blank")
  private String title;

  @NotNull(message = "The owner can't be null")
  private User owner;

  public Hashtag toHashtag() {
    return new Hashtag(null, this.title, this.owner, null);
  }
}
