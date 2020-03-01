FROM  openjdk:11-slim

ADD build/libs/mail-service-0.0.3-SNAPSHOT.jar /opt/service/service.jar

WORKDIR /opt/service/
ENTRYPOINT java -jar service.jar --spring.profiles.active=prod
