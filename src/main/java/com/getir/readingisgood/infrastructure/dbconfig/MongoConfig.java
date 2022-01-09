package com.getir.readingisgood.infrastructure.dbconfig;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;


@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  private static final String DATABASE_NAME = "GETIR";
  private static final String COLLECTION_NAME = "com.getir";

  @Value("${mongo-db.url}")
  private String dbUrl;

  @Override
  protected String getDatabaseName() {
    return DATABASE_NAME;
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString(dbUrl);
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Override
  public Collection getMappingBasePackages() {
    return Collections.singleton(COLLECTION_NAME);
  }
}
