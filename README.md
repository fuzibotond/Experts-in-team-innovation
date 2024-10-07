Sensor Dashboard & Data Processing

## Overview
This project is a microservices-based architecture for collecting and processing sensor data. It uses Docker Compose to deploy the services, including Kafka, Spark, MongoDB, a Java backend, and an Angular frontend.

## Prerequisites
- Docker & Docker Compose
- Git

Quick Start

1. **Clone the Repository**:
   ```bash
   git remote add origin https://github.com/fuzibotond/Experts-in-team-innovation.git
   cd Experts-in-team-innovation
   ```

2. **Start the Services**:
   ```bash
   docker-compose up --build
   ```

3. **Access the Application**:
   - **Frontend**: [http://localhost:4200](http://localhost:4200)
   - **Java Backend API**: [http://localhost:8085](http://localhost:8085)
   - **MongoDB**: Accessible at `mongodb://localhost:27017`
   - **Kafka**: Running on `localhost:9092`
   - **Spark Master Dashboard**: [http://localhost:8081](http://localhost:8081)
   - **Spark Worker Dashboard**: [http://localhost:8082](http://localhost:8082)

4. **Stop the Services**:
   ```bash
   docker-compose down
   ```

## Directory Structure
- `flask-spark-data-processing/`: Flask service for data processing.
- `java-server/`: Java backend for sensor data management.
- `sensor-dashboard/`: Angular frontend for the dashboard.
- `docker-compose.yml`: Docker Compose setup file.
