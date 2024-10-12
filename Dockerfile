FROM openjdk:21-jdk
ARG JAR=build/libs/*.jar
COPY ${JAR} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
