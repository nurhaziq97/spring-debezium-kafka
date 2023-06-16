# Spring Boot with Debezium and Kafka Sample

This is a sample Spring Boot project that showcases the integration of Debezium and Kafka for change data capture (CDC) in a database.

## Prerequisites

Before running this project, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK) 11 or higher
- Docker
- docker-compose

---
## Getting Started

Follow these steps to get started with the project:

1. Clone the repository:
```bash 
git clone https://github.com/nurhaziq97/spring-debezium-kafka.git
```

2. Navigate to the project directory:

```bash
cd spring-debezium-kafka
```

3. Update the Kafka and DB configuration:

    Open the ```src/main/resources/application.properties``` file and modify the Kafka bootstrap servers configuration to match your Kafka setup.

4. Start and run docker

    Navigate to the project resource directory that contains ```docker-compose.yml``` and change the setting if needed. Ones changed run ```docker-compose up -d``` to start and run the docker. Check the docker status by running ```docker ps -a``` and ensure that 5 docker image is running.

5. Setup database

    For example, we use the database setup in docker-compose.

    1. Access the docker image of the postgresql for the source db

        ```bash
        docker exec -it <postgresql_image_id> bash
        psql -U postgres        
        ```
       Run  the sql command to create a sample database
        ```sql
            -- run this for the sake of example
            -- Create the users table
            CREATE TABLE users (
            id SERIAL PRIMARY KEY,
            username VARCHAR(50) NOT NULL,
            email VARCHAR(100) NOT NULL,
            password VARCHAR(100) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );

            -- Create the posts table
            CREATE TABLE posts (
            id SERIAL PRIMARY KEY,
            title VARCHAR(100) NOT NULL,
            content TEXT NOT NULL,
            author_id INTEGER NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
            );

            -- Create the comments table
            CREATE TABLE comments (
            id SERIAL PRIMARY KEY,
            post_id INTEGER NOT NULL,
            user_id INTEGER NOT NULL,
            content TEXT NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
            FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
            );
        ```

    2. Access the docker image of the mysql for the target db

        1. Enter the mysql
        ```bash
        docker exec -it <docker_image_id> bash
        mysql -u root -p
        ```
        2. create sample database
        ```bash
        CREATE DATABASE <DB_NAME>
        ```
    

6. Update the application.properties if necessary

7. Run the following command to start the Spring Boot application:

    ```bash
    ./mvnw spring-boot:run
    ```

8. Test the application by adding the data in the source db and check the target db.

---
    
## How It Works

This project demonstrates the usage of Debezium, which is an open-source CDC platform, with Spring Boot and Kafka. Debezium captures change events from the MySQL database and publishes them to a Kafka topic. The Spring Boot application consumes the change events from the Kafka topic and processes them.

The project uses the Debezium MySQL connector to monitor the database for changes. The connector is configured in the src/main/resources/application.properties file. It listens to the specified MySQL server and captures the changes in the configured database and tables.

The Spring Boot application consumes the change events using the Spring Kafka library. It provides an endpoint to create, update, and delete entities in the sample database. When these operations are performed, the corresponding change events are captured by Debezium, published to the Kafka topic cdc-events, and consumed by the Spring Boot application.