FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y bash bash-completion vim && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the built jar from target folder
COPY target/*.jar app.jar

# Agar aapke paas application.properties local hai to copy karo
COPY src/main/resources/application.properties ./application.properties


ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=classpath:/application.properties,file:/app/application.properties"]

