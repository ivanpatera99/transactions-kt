FROM maven:3-eclipse-temurin-22-alpine as builder

# Copy your source code to the container
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

# Build a release artifact.
RUN mvn -f /usr/src/app/pom.xml clean package

# Use OpenJDK 22 base image
FROM openjdk:22-jdk

# Copy the jar file built in the first stage
COPY --from=builder /usr/src/app/target/*.jar /usr/app/app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the jar file 
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]