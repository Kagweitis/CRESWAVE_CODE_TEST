# Stage 1: Build stage
FROM amazoncorretto:17 AS build

#Install maven
RUN apt-get update
RUN apt-get install -y maven

# Copy the source code and build
COPY . /code
WORKDIR /code
RUN mvn package -DskipTests

#create image
COPY --from=build /app/target/CRESWAVE_CODE_TEST-0.0.1-SNAPSHOT.jar /CRESWAVE_CODE_TEST-0.0.1-SNAPSHOT.jar

EXPOSE 3326

ENTRYPOINT ["java","-jar","target/CRESWAVE_CODE_TEST-0.0.1.jar"]
