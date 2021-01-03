package com.challenge.markhashtags.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tweet implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private Long tweetId;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String message;

  @Column(length = 100, nullable = false)
  private String author;

  @Column(length = 45, nullable = false)
  private String date;

  @Column(length = 120)
  private String authorAvatar;

  @ManyToOne
  @JoinColumn(name = "hashtag_id", nullable = false)
  private Hashtag hashtag;
}
