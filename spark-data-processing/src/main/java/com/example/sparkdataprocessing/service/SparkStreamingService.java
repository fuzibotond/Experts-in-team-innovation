package com.example.sparkdataprocessing.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class SparkStreamingService {

    private final JavaStreamingContext streamingContext;

    public SparkStreamingService(JavaStreamingContext streamingContext) {
        this.streamingContext = streamingContext;
    }

    public void startStream() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "sensor-group");
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", false);

        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(Arrays.asList("sensor-data-topic"), kafkaParams)
        );

        stream.foreachRDD(rdd -> {
            rdd.foreach(record -> {
                System.out.println("Received: " + record.value());
                // Here you can implement your logic to process the data using Spark
            });
        });

        try {
            streamingContext.start();
            streamingContext.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
