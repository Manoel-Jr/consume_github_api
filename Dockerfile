FROM openjdk:17-alpine
COPY /target/info-github.jar /app/info-github.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "info-github.jar"]