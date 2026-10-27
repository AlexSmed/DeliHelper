FROM ubuntu:latest
LABEL authors="Aschl"

ENTRYPOINT ["top", "-b"]


FROM amazoncorretto:17-alpine

RUN apk add --no-cache curl

COPY target/app.jar /app.jar

EXPOSE 7070

CMD ["java", "-jar", "/app.jar"]
