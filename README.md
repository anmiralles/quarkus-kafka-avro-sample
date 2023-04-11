# Demo of Quarkus, Kafka and Avro

The ordering of events is particularly relevant if you are using an "event sourcing" approach, the state of an aggregate
object is derived from a log of events by replaying them in a particular order. Thus, even though there may be many 
different event types, all events that define an aggregate must go in the same topic.

When working with an avro registry, like Apicurio, you rely on the assumption that there is one schema for each topic 
(or rather, one schema for the key and one for the value of a message). You can register new versions of a schema, 
and the registry checks that the schema changes are forward and backward compatible.

Then, how can we achieved dynamic schema resolution using one topic? The answer is changing the Strategy to map the 
Kafka message to a schema artifact in Apicurio Registry.

Apicurio Registry provides the following strategies to return a reference to an artifact:

- RecordIdStrategy,
Avro-specific strategy that uses the full name of the schema.
- TopicRecordIdStrategy,
Avro-specific strategy that uses the topic name and the full name of the schema.
- TopicIdStrategy,
Default strategy that uses the topic name and key or value suffix.
- SimpleTopicIdStrategy,
Simple strategy that only uses the topic name.

Then the Kafka client deserializer uses lookup strategies to determine the artifact ID and global ID under which the 
message schema is registered in Apicurio Registry. For a given topic and message, you can use different implementations 
of the ArtifactReferenceResolverStrategy Java interface to return a reference to an artifact in the registry.

## Prerequisites

```bash
docker-compose up -d
```

_NOTE:_ stop the infrastructure with: `docker-compose down; docker-compose rm`

## Build and Run

```bash
mvn compile quarkus:dev
```

To build the native executable:

```bash
mvn package -Pnative
```

## Demo

```bash
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"title":"The Shawshank Redemption","year":1994}' \
  http://localhost:8080/movies/create

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"title":"The Godfather","year":1972}' \
  http://localhost:8080/movies/update
```
