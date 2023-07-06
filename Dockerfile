FROM openjdk:17-jdk-alpine
ADD target/challenge-0.0.1-SNAPSHOT.jar challenge-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java", "-jar","challenge-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
