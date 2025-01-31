FROM openjdk:18
WORKDIR /app
COPY ./target/server-service.jar /app
EXPOSE 8761
CMD ["java", "-jar", "server-service.jar"]