package de.abramov.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
    private static Logger logger = LoggerFactory.getLogger(BackendApplication.class);


    public static void main(String[] args) {
        logger.info("Engine starts... {}", "Spring Rocket Launch...");
        logger.info("************************");
        SpringApplication.run(BackendApplication.class, args);
    }

}
