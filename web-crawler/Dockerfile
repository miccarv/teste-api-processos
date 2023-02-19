# Use the official Node.js runtime as a parent image
FROM node:16

# Set the working directory to /app
WORKDIR /web-crawler

# Copy the package.json and package-lock.json files to the container
COPY package*.json ./

# Install dependencies
RUN npm install
RUN npm i -S request request-promise cheerio express

# Copy the rest of the application code to the container
COPY . .

# Expose the port on which the application will listen
EXPOSE 8081

# Start the Node.js application
CMD [ "node", "server" ]