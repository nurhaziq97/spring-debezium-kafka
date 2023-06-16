package com.example.KafkaDebeziumExp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
}
