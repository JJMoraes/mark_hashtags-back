package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    public List<Tweet> findAllByHashtagIdOrderByIdDesc(Long id);

    public Optional<Tweet> findTopByHashtagIdOrderByIdDesc(Long id);
}
