# Stage 1: Build stage
FROM maven:3.8.4-openjdk-17 AS build

##Install maven
#RUN apt-get update
#RUN apt-get install -y maven

# Copy the source code and build
COPY . /code
WORKDIR /code
RUN mvn package -DskipTests

# Stage 2: Final stage
FROM amazoncorretto:17

#create image
COPY --from=build /code/CRESWAVE_CODE_TEST-0.0.1-SNAPSHOT.jar /CRESWAVE_CODE_TEST-0.0.1-SNAPSHOT.jar

EXPOSE 3326

ENTRYPOINT ["java","-jar","target/CRESWAVE_CODE_TEST-0.0.1.jar"]
