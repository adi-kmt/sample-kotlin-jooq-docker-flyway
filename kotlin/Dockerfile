FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/kotlin-*-SNAPSHOT.jar /service.jar

# Expose port 8080
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "/service.jar"]