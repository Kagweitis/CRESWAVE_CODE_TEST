FROM amazoncorretto:17
#Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

#Copy the SRC, LIB and pom.xml to WORKDIR
ADD pom.xml /code/pom.xml
ADD lib /code/lib
ADD src /code/src

EXPOSE 3326

ENTRYPOINT ["java","-jar","target/CRESWAVE_CODE_TEST-0.0.1.jar"]
