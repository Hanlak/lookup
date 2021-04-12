FROM openjdk:8-jdk-alpine
ADD target/lookup-docker.jar lookup-docker.jar
ENTRYPOINT ["java", "-jar" , "lookup-docker.jar"]