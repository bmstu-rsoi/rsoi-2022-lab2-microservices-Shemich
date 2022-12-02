FROM openjdk:17-jdk-alpine
RUN mkdir /app
WORKDIR app
EXPOSE 8070
ARG JAR_FILE=build/libs/ticket-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]