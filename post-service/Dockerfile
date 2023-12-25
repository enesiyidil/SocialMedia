FROM amazoncorretto:19
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD /build/libs/post-service-v.0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]