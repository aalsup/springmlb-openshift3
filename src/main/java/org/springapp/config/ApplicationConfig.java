package org.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
public class ApplicationConfig {

	@Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String openshiftMongoDbHost = System.getenv("MONGODB_HOST");
        if(openshiftMongoDbHost == null){
        	return new MongoTemplate(new Mongo(), "springmlb");
        }
        int openshiftMongoDbPort = 27017;
        String username = System.getenv("MONGODB_USER");
        String password = System.getenv("MONGODB_PASSWORD");
        Mongo mongo = new Mongo(openshiftMongoDbHost, openshiftMongoDbPort);
        UserCredentials userCredentials = new UserCredentials(username, password);
        String databaseName = System.getenv("MONGODB_DATABASE");
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
