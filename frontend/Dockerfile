# Use an official Node.js runtime as a parent image
FROM node:14.17.6-alpine

# Set the working directory to /frontend
WORKDIR /app

# Copy the package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application files to the container
COPY . .

# Set the command to start the app
CMD [ "npm", "start" ]