FROM nginx:latest

COPY assets/ /usr/share/nginx/html/assets
COPY pages/ /usr/share/nginx/html/pages 

RUN rm /usr/share/nginx/html/index.html
RUN ln -s /usr/share/nginx/html/pages/login/login.html /usr/share/nginx/html/index.html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
