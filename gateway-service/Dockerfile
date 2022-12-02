FROM openjdk:17-jdk-alpine
RUN mkdir /app
WORKDIR app
EXPOSE 8080
ARG JAR_FILE=build/libs/gateway-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]