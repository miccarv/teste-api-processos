FROM openjdk:19-jdk

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/app.jar .

EXPOSE 8080

# Set the default command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]