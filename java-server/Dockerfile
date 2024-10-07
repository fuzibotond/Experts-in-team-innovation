FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/java-server-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8085

CMD ["java", "-jar", "app.jar"]
