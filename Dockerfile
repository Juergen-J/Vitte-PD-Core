FROM openjdk:21-jdk-slim as build

RUN apt-get update && apt-get install -y wget gnupg2 software-properties-common unzip

WORKDIR /app
COPY . .

CMD ["java", "-jar", "target/helpdesk-0.0.1-SNAPSHOT.jar"]