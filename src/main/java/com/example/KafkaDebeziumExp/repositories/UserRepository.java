package com.example.KafkaDebeziumExp.repositories;

import com.example.KafkaDebeziumExp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
