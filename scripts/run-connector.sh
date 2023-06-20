curl -i -X POST -H "Accept: application/json" -H "Content-Type:application/json"\
 localhost:8083/connectors/ --data-binary "@./SOURCE_DB2_DEBEZIUM_KAFKA.json"