# Use an official OpenJDK 17 runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
# Make sure your JAR file is already built using Maven/Gradle before proceeding.
COPY target/User-0.0.1-SNAPSHOT.jar /app/User-0.0.1-SNAPSHOT.jar

# Expose the port the app will run on (as per your application.properties)
EXPOSE 6576

# Run the application
ENTRYPOINT ["java", "-jar", "/app/User-0.0.1-SNAPSHOT.jar"]

