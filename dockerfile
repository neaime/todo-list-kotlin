FROM java:8
WORKDIR /todo-list
VOLUME /var/lib/docker/volumes/captain--todo-list/_data:/todo-list
EXPOSE 2000
CMD java -jar todolist-0.1.jar
