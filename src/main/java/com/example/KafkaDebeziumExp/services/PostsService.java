package com.example.KafkaDebeziumExp.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.KafkaDebeziumExp.entities.Posts;
import com.example.KafkaDebeziumExp.entities.Users;
import com.example.KafkaDebeziumExp.repositories.PostsRepository;
import com.example.KafkaDebeziumExp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.debezium.data.Envelope.Operation;
import lombok.extern.slf4j.Slf4j;

@Service("posts")
@Slf4j
public class PostsService implements DBService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    public PostsService(PostsRepository repository, UserRepository userRepository) {
        this.postsRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void replicateData(Map<String, Object> dbData, Operation operation) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Posts posts = objectMapper.convertValue(dbData, Posts.class);
        log.info("Data fetch from debezium: {}", dbData);
        Long authorId = dbData.get("author_id") != null
                ? ((Integer) dbData.get("author_id")).longValue()
                : -1;
        Users user = userRepository.findById(authorId).get();
        if (user != null) {
            posts.setAuthorId(user);
            if (Operation.DELETE == operation) {
                postsRepository.deleteById(posts.getId());
            } else {
                postsRepository.save(posts);
            }
        }
    }

}
