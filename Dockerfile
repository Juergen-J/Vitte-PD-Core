FROM openjdk:21-jdk-slim as build

RUN apt-get update && apt-get install -y wget gnupg2 software-properties-common unzip maven

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/helpdesk-0.0.1-SNAPSHOT.jar ./

CMD ["java", "-jar", "helpdesk-0.0.1-SNAPSHOT.jar"]
