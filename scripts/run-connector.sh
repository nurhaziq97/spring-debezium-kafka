curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json"\
 localhost:8083/connectors/ --data-binary "@./SOURCE_DB2_DEBEZIUM_KAFKA_POSTS.json"

# curl -i -X PUT -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/example-db2-connector-v0.1/config --data-binary "@./SOURCE_DB2_DEBEZIUM_KAFKA.json"
#  curl -i -X DELETE localhost:8083/connectors/example-db2-connector-posts-v0.1/