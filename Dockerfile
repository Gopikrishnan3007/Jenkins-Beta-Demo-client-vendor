FROM openjdk:18
WORKDIR /app
COPY ./target/vendor-service.jar /app
EXPOSE 8761
CMD ["java", "-jar", "vendor-service.jar"]
