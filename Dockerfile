FROM gradle:7.5.0-jdk11
WORKDIR /anti-fraud
COPY build.gradle ./
COPY src/ ./src/
RUN gradle build
CMD ["java", "-jar", "/anti-fraud/build/libs/anti-fraud.jar"]

