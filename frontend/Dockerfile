FROM node:22 AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY angular.json tsconfig*.json ./
COPY src ./src
COPY public ./public

ARG BUILD_CONFIGURATION=production
RUN npm run build -- --configuration $BUILD_CONFIGURATION

FROM nginxinc/nginx-unprivileged:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
