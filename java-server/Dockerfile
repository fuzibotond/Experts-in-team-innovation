# Use a lightweight base image with OpenJDK 17
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/java-server-1.0-SNAPSHOT.jar /app/java-server.jar

# Expose the application's port
EXPOSE 8085

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "java-server.jar"]
