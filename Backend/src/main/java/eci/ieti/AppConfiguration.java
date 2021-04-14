package eci.ieti;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.MongoClientURI;

@Configuration
public class AppConfiguration {
	
	@Value("${spring.data.mongodb.uri}")
	private String connectionString;

    @Bean
	public MongoDbFactory mongoDbFactory() throws Exception {

		// Create URI
		MongoClientURI mongoClientUri = new MongoClientURI(connectionString);

		// Create DB factory
		return new SimpleMongoDbFactory(mongoClientUri);
	}

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        MongoTemplate mongoTemplate = mongoTemplate();
        return new GridFsTemplate(mongoDbFactory(), mongoTemplate.getConverter());
    }



}

