# ---------- Stage 1 : Build ----------
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copie les fichiers Maven en premier (pour profiter du cache Docker)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copie le code source et build le jar
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---------- Stage 2 : Run ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copie uniquement le jar généré depuis le stage précédent
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]