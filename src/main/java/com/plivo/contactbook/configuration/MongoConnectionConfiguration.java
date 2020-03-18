package com.plivo.contactbook.configuration;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.inject.Inject;

@Configuration
@Profile("!test")
@EnableMongoRepositories("com.plivo.contactbook.repository")
public class MongoConnectionConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.minConnections}")
    private String minConnections;

    @Value("${spring.data.mongodb.maxConnections}")
    private String maxConnections;

    @Inject
    private ConfigurableEnvironment environment;

    private static final String LOCAL = "local";
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    public MongoConnectionConfiguration(ConfigurableEnvironment environment){
        this.environment = environment;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {

        // Configuring MongoClientBuilder
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        builder.minConnectionsPerHost(Integer.parseInt(minConnections));
        builder.connectionsPerHost(Integer.parseInt(maxConnections));

        MongoClientURI mongoClientURI;
        mongoClientURI = new MongoClientURI(mongoUri, builder);
        MongoClient mongoClient = MongoClients.create(mongoUri);

        return mongoClient;
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @RequiredArgsConstructor
    @Configuration
    public class MongoConfiguration {

        private final MongoTemplate mongoTemplate;

        private final MongoConverter mongoConverter;

        @EventListener(ApplicationReadyEvent.class)
        public void initIndicesAfterStartup() {

            var init = System.currentTimeMillis();

            var mappingContext = this.mongoConverter.getMappingContext();

            if (mappingContext instanceof MongoMappingContext) {
                MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
                for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                    var clazz = persistentEntity.getType();
                    if (clazz.isAnnotationPresent(Document.class)) {
                        var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

                        var indexOps = mongoTemplate.indexOps(clazz);
                        resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                    }
                }
            }

        }
    }
}
