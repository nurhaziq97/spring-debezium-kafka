package com.example.KafkaDebeziumExp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.KafkaDebeziumExp.entities.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>{
    
}
