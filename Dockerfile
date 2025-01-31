FROM openjdk:18
WORKDIR /app
COPY ./target/client-service.jar /app
EXPOSE 5365
CMD ["java", "-jar", "client-service.jar"]
