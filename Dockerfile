FROM bellsoft/liberica-openjdk-alpine:17
ARG JAR_FILE=build/libs/hackathon-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/myboot.jar

ENTRYPOINT ["java","-jar","/app/myboot.jar"]