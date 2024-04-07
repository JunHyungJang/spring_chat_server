FROM openjdk:17-jdk
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=target/spring-websockets-stompjs-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]