FROM openjdk:18
WORKDIR /app
COPY ./target/api-service.jar /app
EXPOSE 8006
CMD ["java", "-jar", "api-service.jar"]
