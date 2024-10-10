# Use a node image for building the frontend
FROM node:18-alpine as build

WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the application code and build it
COPY . .
RUN npm run build --prod

# Use a lightweight web server image to serve the frontend
FROM nginx:alpine
COPY --from=build /app/dist/sensor-dashboard/browser /usr/share/nginx/html

# Expose the default port
EXPOSE 80
