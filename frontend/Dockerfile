# Description: Dockerfile for the frontend service

# Import the official Node.js image
FROM node:18 as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
RUN npm install marked
COPY . .
RUN npm run build

# Import the official Nginx image
FROM nginx:stable-alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copy the Nginx configuration files
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
