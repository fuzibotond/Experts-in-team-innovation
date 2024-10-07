package com.example.sparkdataprocessing.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;


@Configuration
public class SparkConfig {

    @Bean
    public JavaSparkContext sparkContext() {
        SparkConf conf = new SparkConf()
                .setAppName("SparkDataProcessing")
                .setMaster("local"); // Adjust spark master URL based on your setup
        return new JavaSparkContext(conf);
    }

    @Bean
    public JavaStreamingContext streamingContext(JavaSparkContext sc) {
        return new JavaStreamingContext(sc, Durations.seconds(10));
    }
}
