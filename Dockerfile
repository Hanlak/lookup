FROM openjdk:8
ADD target/lookup-docker.jar lookup-docker.jar
ENTRYPOINT ["java", "-jar" , "lookup-docker.jar"]