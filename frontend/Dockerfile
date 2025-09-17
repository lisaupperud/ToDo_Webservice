# ---------- Stage 1: Build the frontend ----------
FROM node:20 AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# ---------- Stage 2: Serve with Nginx ----------
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy build output
COPY --from=build /app/dist .

# Copy custom nginx config (optional, for React routing)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose production port
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]


