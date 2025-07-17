# Step 1: Build the application
FROM eclipse-temurin:17 as builder
WORKDIR /app

# Copy only the wrapper & pom.xml to download dependencies
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Pre-download dependencies (this step is cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]