package com.challenge.markhashtags.service;

import com.challenge.markhashtags.domain.Hashtag;
import com.challenge.markhashtags.domain.User;
import com.challenge.markhashtags.exception.especific.HashtagNotFoundException;
import com.challenge.markhashtags.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HashtagService {

    private HashtagRepository hashtagRepository;

    public Hashtag save(Hashtag hashtag){
        return hashtagRepository.save(hashtag);
    }

    public Hashtag getById(Long id){
        return hashtagRepository.findById(id).orElseThrow(HashtagNotFoundException::new);
    }

    public List<Hashtag> getAllByOwnerId(Long id){
        return hashtagRepository.findAllByOwnerId(id);
    }

    public void delete(Long id){
        Hashtag hashtag = getById(id);
        hashtagRepository.delete(hashtag);
    }

}
