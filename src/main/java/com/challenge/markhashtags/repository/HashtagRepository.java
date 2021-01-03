package com.challenge.markhashtags.repository;

import com.challenge.markhashtags.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    public List<Hashtag> findAllByOwnerId(Long id);

}
