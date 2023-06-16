package com.example.KafkaDebeziumExp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Posts{
    @Id
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    private Users authorId;
}