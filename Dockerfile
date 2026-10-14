FROM eclipse-temurin:25-jdk

COPY ./target/app.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "app.jar", "db:3306", "10000"]