package com.example.KafkaDebeziumExp.debezium;

import com.example.KafkaDebeziumExp.services.UserService;
import io.debezium.config.Configuration;
import io.debezium.data.Envelope.Operation;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class DebeziumListener {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final UserService userService;
    private DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    @PostConstruct
    private void start(){
        this.executor.execute(debeziumEngine);
    }
    @PreDestroy
    private void stop() throws IOException {
        if(this.debeziumEngine != null){
            this.debeziumEngine.close();
        }
    }

    public DebeziumListener(Configuration usersConnectorConfigs, UserService userService){
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(usersConnectorConfigs.asProperties())
                .notifying(this::handleChangeEvent)
                .build();
        this.userService = userService;
    }

    /**
     * Handle change event by copying all the events from source to target
     * @param sourceRecordRecordChangeEvent handle the record changes from source to target method
     */
    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent){
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        log.info("key = '{}' \n value = '{}'", sourceRecord.key(), sourceRecord.value());
        Struct sourceRecordChangeValue = (Struct) sourceRecord.value();
        if(sourceRecordChangeValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
            if(operation != Operation.READ){
                String record = operation == Operation.DELETE ? BEFORE : AFTER;
                Struct struct = (Struct) sourceRecordChangeValue.get(record);
                Map<String, Object> payload = struct.schema().fields().stream()
                        .map(Field::name)
                        .filter(fieldName -> struct.get(fieldName) != null)
                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                        .collect(toMap(Pair::getKey, Pair::getValue));

                this.userService.replicateData(payload, operation);
                log.info("Uploaded Data: {} with Operation: {}", payload, operation.name());
            }
        }
    }
}
