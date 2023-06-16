package com.example.KafkaDebeziumExp.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersDAO {
    private String username;
    private String email;
}
