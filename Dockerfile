FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw mvnw.cmd pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:resolve dependency:go-offline

COPY src/ src/

RUN ./mvnw clean package -DskipTests

RUN ls -l target/

EXPOSE 5433

CMD ["java", "-jar", "target/ssalud-0.0.1-SNAPSHOT.jar"]