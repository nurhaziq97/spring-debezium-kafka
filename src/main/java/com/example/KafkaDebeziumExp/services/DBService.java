package com.example.KafkaDebeziumExp.services;

import java.util.Map;

import io.debezium.data.Envelope.Operation;

public interface DBService {
    void replicateData(Map<String, Object> dbData, Operation operation);
}
