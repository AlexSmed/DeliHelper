FROM ubuntu:latest
LABEL authors="Aschl"

ENTRYPOINT ["top", "-b"]

FROM amazoncorretto:17-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY target/DeliHelper-1.0-SNAPSHOT.jar /app.jar

EXPOSE 7070

CMD ["java", "-jar", "/app.jar"]
