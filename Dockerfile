# Step 1: Build the application
FROM eclipse-temurin:17 as builder
WORKDIR /app

# Copy wrapper and POM first to leverage caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make sure the wrapper script is executable (for Linux)
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the app source code
COPY src ./src

# Package the app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
