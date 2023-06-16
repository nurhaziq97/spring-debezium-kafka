package com.example.KafkaDebeziumExp.services;

import com.example.KafkaDebeziumExp.entities.Users;
import com.example.KafkaDebeziumExp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope.Operation;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * replicate userData from source (producer) to another target db through debezium and kafka
     * @param userData data from source
     * @param operation operation done in source and get through debezium
     */
    public void replicateData(Map<String, Object> userData, Operation operation){
        final ObjectMapper  objectMapper = new ObjectMapper();
        final Users user = objectMapper.convertValue(userData, Users.class);
        if(Operation.DELETE == operation){
            userRepository.deleteById(user.getId());
        }else {
            userRepository.save(user);
        }
    }
}
