FROM openjdk:18
WORKDIR /app
COPY ./target/tap-api-v1-apigateway-0.0.1-SNAPSHOT.jar /app
EXPOSE 8006
CMD ["java", "-jar", "tap-api-v1-apigateway-0.0.1-SNAPSHOT.jar"]
