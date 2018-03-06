# spring-boot-webflux-cassandra-reactive

CREATE KEYSPACE IF NOT EXISTS example WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}
CREATE TABLE example.person(id UUID PRIMARY KEY, name varchar, dob timestamp);