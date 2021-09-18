## start zookeper server:
* bin\windows\zookeeper-server-start.bat config\zookeeper.properties

## start kafka server:
* bin\windows\kafka-server-start.bat config\server.properties


## create a topic:
* bin\windows\kafka-topics.bat --create --zookeper localhost:2181 --replication-factor 1 --partitions 3 --topic streams-plaintext-input


## print the list of all the topics :
* bin\windows\kafka-topics.bat --list --zookeeper localhost:2181


## produce messages from console:
* bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic streams-plaintext-input

## Command for console consumer
* bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic counts --from-beginning 
  --formatter kafka.tools.DefaultMessageFormatter 
  --property print.key=true 
  --property print.value=true 
  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer 
  --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer 