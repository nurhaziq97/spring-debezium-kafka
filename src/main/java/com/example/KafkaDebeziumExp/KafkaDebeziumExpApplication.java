package com.example.KafkaDebeziumExp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableAutoConfiguration  // (exclude = { KafkaAutoConfiguration.class })
@EnableKafka
public class KafkaDebeziumExpApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDebeziumExpApplication.class, args);
	}

}
