# Data Collection System for Automation Solutions
Experts in team innovation

## Overview
This project aims to develop a **secure**, and **scalable data collection system** for automation solutions in manufacturing environments. The system uses **Raspberry Pi** as an edge computing hub to collect data from various sensors (2D/3D cameras, vibration sensors, power sensors), pre-process the data, and transmit it securely to the cloud for real-time processing and analysis.

## Problems Addressed
- **Data Collection and Processing**: Handling large amounts of heterogeneous data from multiple sensors.
- **Security**: Ensuring end-to-end encryption and access control from data collection to visualization.
- **Technical Limitations**: Overcoming storage, communication, and energy constraints in edge devices.

## Project Goals
- **Multi-Sensor Data Collection**: Support for various sensor types (visual, vibration, power) on Raspberry Pi.
- **Preprocessing and Edge Computing**: Data preprocessing (compression/filtering) to reduce bandwidth and improve efficiency.
- **Secure Data Transmission**: Implementing **TLS/SSL** for secure data transmission via MQTT, and **AES encryption** for data at rest.
- **Cloud Integration**: Use **MongoDB** for secure, scalable storage and **Apache Spark** for real-time/batch data processing.
- **Visualization**: TODO.

## Key Features
- **Edge Computing**: Preprocessing on Raspberry Pi, reducing the load on the cloud.
- **Secure Communication**: End-to-end encryption and role-based access control.
- **Real-time Processing**: Using Apache Spark to provide real-time insights and batch processing for analytics.
- **Visualization Dashboard**: TODO.

## Technology Stack
- **Edge Device**: Raspberry Pi (Python, OpenCV, Numpy)
- **Communication**: MQTT (Mosquitto Broker, TLS/SSL)
- **Cloud Storage**: MongoDB (Free-tier)
- **Data Processing**: Apache Spark (Real-time and batch processing)
- **Visualization**: TODO

## Project Structure
1. **Data Collection from Sensors**
   - Raspberry Pi setup, data collection scripts, and preprocessing.

2. **Edge Security and Data Transmission**
   - Secure data encryption, firewall, and MQTT setup.

3. **Cloud Storage and Processing**
   - MongoDB setup, data ingestion via MQTT, and cloud-based processing.

4. **Visualization and Monitoring**
   - Grafana dashboard integration for real-time visualization and reporting.

## Documentation
- **Lecture report:** 
- **Group report:**
- **Architecture diagramm:** https://drive.google.com/file/d/18rGGxSGw3XwXnwWg__I8JwvKpIeWuB_B/view?usp=sharing
- **Scope and project description:**
- **Team contract:** https://docs.google.com/document/d/1AwL4ppirnH3McH4XpLjfCqD5HfUx5xqh-ucVEgvVfF4/edit?usp=sharing
- **Questions:** https://docs.google.com/document/d/1MhpU9-iZGjihDWCyyB5e3uR8SlVTCHAWidYPyjr71D4/edit?pli=1
