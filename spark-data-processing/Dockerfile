# Use an official OpenJDK runtime as a parent image
FROM openjdk:20-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build artifact (your Spring Boot JAR file)
COPY target/spark-data-processing-0.0.1-SNAPSHOT.jar /app/spark-data-processing.jar

# Expose the port that your Spring Boot app listens on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/spark-data-processing.jar"]
