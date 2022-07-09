FROM openjdk:11-jre-slim

RUN mkdir home/app

WORKDIR /home/app

ADD target/tahawyDB-0.0.1-SNAPSHOT.jar /home/app

ENTRYPOINT ["java","-jar","tahawyDB-0.0.1-SNAPSHOT.jar"]
