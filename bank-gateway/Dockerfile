FROM openjdk:21-ea-34-jdk-slim-bookworm
VOLUME /tmp

ARG DEPENDENCY=/build
COPY ${DEPENDENCY}/libs /app
COPY ${DEPENDENCY}/*.jar /app

ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

ENV PORT 8080
EXPOSE 8080

USER root
ENTRYPOINT [ "sh", "-c", "java -jar app/bank-gateway-1.0.1.jar" ]