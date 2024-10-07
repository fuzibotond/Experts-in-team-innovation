# Use a base image that includes Python and Java
FROM openjdk:11-jre-slim

# Install Python and pip
RUN apt-get update && apt-get install -y python3 python3-pip

# Set the working directory inside the container
WORKDIR /app

# Copy your application code
COPY . .

# Install required Python packages
RUN pip3 install --no-cache-dir -r requirements.txt

# Install the 'ps' command
RUN apt-get install -y procps

# Set JAVA_HOME for Spark
ENV JAVA_HOME=/usr/local/openjdk-11
ENV PATH=$JAVA_HOME/bin:$PATH

# Expose the port your app will run on
EXPOSE 8080

# Command to run your Flask app
CMD ["python3", "app.py"]
