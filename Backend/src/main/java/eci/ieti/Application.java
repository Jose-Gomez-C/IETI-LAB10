package eci.ieti;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.client.gridfs.model.GridFSFile;

@SpringBootApplication
public class Application implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    GridFsTemplate gridFsTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is("testing.png")));
        URL url = new URL("https://i.dailymail.co.uk/i/pix/tm/2007/07/lionking1807_468x325._to_468x312jpeg");
        gridFsTemplate.store(url.openStream(), "lion.jpeg",  "image/jpeg");
        logger.info("Image successfully uploaded to the database");
    }
}