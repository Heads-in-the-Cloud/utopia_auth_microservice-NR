FROM openjdk:8-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE ${UTOPIA_MICROSERVICE_AUTH_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]