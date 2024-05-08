FROM amazoncorretto:17
#Install maven
RUN apt-get update
RUN apt-get install -y maven
COPY . /code
WORKDIR /code
RUN mvn package -DskipTests

#create a slim image
FROM openjdk:11-jre-slim
COPY --from=build /app/target/CRESWAVE_CODE_TEST-0.0.1-SNAPSHOT.jar /CRESWAVE_CODE_TEST-0.0.1-SNAPSHOT.jar

EXPOSE 3326

ENTRYPOINT ["java","-jar","target/CRESWAVE_CODE_TEST-0.0.1.jar"]
