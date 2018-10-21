FROM openjdk:8-jdk-alpine
VOLUME /app
WORKDIR /app
RUN apk --no-cache add bash
COPY /target/SpringBoot-Journal-0.0.1-SNAPSHOT.war /app
ADD entrypoint.sh entrypoint.sh
RUN ls
EXPOSE 8080 9990
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
#build image with docker image build .  -t spring-boot-journal-0.0.1
#run with docker container run -p 8080:8080 -p 9000:9000  -d -e SPRING_PROFILES_ACTIVE=dev -e JAVA_ENABLE_DEBUG="true" spring-boot-journal-0.0.1