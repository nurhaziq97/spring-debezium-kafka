package com.example.KafkaDebeziumExp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class DebeziumUserConnectorConfig {
    @Value("${source.datasource.hostname}")
    private String dbHostname;

    @Value("${source.datasource.port}")
    private String dbPort;

    @Value("${source.datasource.user}")
    private String dbUser;

    @Value("${source.datasource.password}")
    private String dbPassword;

    @Value("${source.datasource.dbname}")
    private String dbName;
    @Bean
    public io.debezium.config.Configuration usersConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
                .with("name", "users-postgres-connector-2")
                .with("plugin.name", "pgoutput")
                // .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("connector.class", "io.debezium.connector.db2.Db2Connector")
                .with("database.hostname", dbHostname)
                .with("database.port", dbPort)
                .with("database.user", dbUser)
                .with("database.password", dbPassword)
                .with("database.dbname", dbName)
                // .with("database.server.name", "postgres")
                .with("table.include.list", "blogs.users")
                .with("topic.prefix", "postgreTestDb")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
                .with("slot.name", "springtestslot")
                .with("schema.history.internal.kafka.bootstrap.servers", "kafka:29092")
                .with("schema.history.internal.kafka.topic", "schemahistory.blogs")
                .build();
    }
}
