FROM openjdk:19-jdk

# Set the working directory to /app
WORKDIR /backend

# Copy the current directory contents into the container at /app
COPY . /backend

# Set the default command to run the application when the container starts
CMD ["java", "-jar", "target/app.jar"]