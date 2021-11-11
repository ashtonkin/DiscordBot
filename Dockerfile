FROM openjdk:17-alpine3.14
ARG TOKEN_ARG
ENV TOKEN=$TOKEN_ARG

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} DiscordBot.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "DiscordBot.jar"]