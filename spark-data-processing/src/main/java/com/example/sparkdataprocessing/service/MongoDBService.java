package com.example.sparkdataprocessing.service;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import org.apache.spark.api.java.JavaRDD;
import org.bson.Document;

import java.util.Map;

public class MongoDBService {

    public void saveToMongoDB(JavaRDD<String> rdd) {
        JavaRDD<Document> documents = rdd.map(Document::parse);

        // You can specify additional configurations for writing to MongoDB
        WriteConfig writeConfig = WriteConfig.create((Map<String, String>) rdd.context());

        // Save the documents to MongoDB
        MongoSpark.save(documents, writeConfig);
    }
}
