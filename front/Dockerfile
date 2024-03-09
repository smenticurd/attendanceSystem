# Используйте официальный образ Nginx в качестве базового образа
FROM nginx:alpine

# Удалите стандартную страницу Nginx
RUN rm -rf /usr/share/nginx/html/*

# Скопируйте ваше статическое веб-приложение в директорию Nginx
COPY . /usr/share/nginx/html/
COPY pages /usr/share/nginx/html
COPY assets /usr/share/nginx/html

# Укажите порт, который будет прослушиваться вашим приложением (необязательно)
EXPOSE 80

# Запустите Nginx
CMD ["nginx", "-g", "daemon off;"]