FROM openjdk:8-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]