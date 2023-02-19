# Use an official Node.js runtime as a parent image
FROM node:14.17.6-alpine

# Set the working directory to /frontend
WORKDIR /frontend

# Copy the package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install \
  babel \
  axios \
  bootstrap \
  react \
  react-dom \
  react-imask \
  react-scripts

# Copy the rest of the application files to the container
COPY . .

# Build the app

# Set the command to start the app
CMD [ "npm", "start" ]