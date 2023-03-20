FROM openjdk:17-alpine

WORKDIR /app

COPY target/ada-cardgame-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "ada-cardgame-0.0.1-SNAPSHOT.jar"]
