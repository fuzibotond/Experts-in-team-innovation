package com.example.sparkdataprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SparkDataProcessingApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SparkDataProcessingApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE); // Disable web context
        app.run(args);
    }


}
