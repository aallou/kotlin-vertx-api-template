FROM openjdk:11-jdk-slim AS java-build

WORKDIR /app/

RUN apt-get update && apt-get install -y git
RUN adduser --system --home /var/cache/bootapp --shell /sbin/nologin --uid 1000 bootapp;

# We copy gradle
COPY gradlew .
COPY .gradle .gradle
COPY gradle gradle
COPY build.gradle .

# We copy the .git folder to be able to set the @buildnumber@ used in the application.properties file
COPY .git .git

COPY src src
RUN ./gradlew assemble

# =======================================================================================

FROM openjdk:11-jdk-slim

COPY --from=java-build /etc/passwd /etc/shadow /etc/
COPY --from=java-build /app/build/libs/*-fat.jar  /app/application.jar
COPY --from=java-build /app/build/resources/**  /config/

EXPOSE 8888

USER bootapp
ENV _JAVA_OPTIONS "-XX:MaxRAMPercentage=90 -Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -Dfile.encoding=UTF-8"

ENTRYPOINT ["java", "-jar", "/app/application.jar", "-conf", "/config/conf/application.json"]
