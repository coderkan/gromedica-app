FROM openjdk:8
ADD ./target/app-0.0.1-SNAPSHOT.jar /usr/src/app-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "app-0.0.1-SNAPSHOT.jar"]