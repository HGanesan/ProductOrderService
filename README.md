README:

Build and run
=============

$ mvn clean package

INFO: Installation of Docker and Docker-compose is required!

$ docker-compose -f ./_docker/application/docker-compose-example.yml up -d --build
TIP: Check if the database is started correctly (127.0.0.1:3309 u:root p:hello)! For the first time you have to create a empty database with file ./database_model/poi_database.sql

Start Docker
============

$ docker-compose -f ./_docker/application/docker-compose-it.yml up -d --build
TIP: Check if the database is started correctly (127.0.0.1:3306 u:root p:hello)

NOTE: this uses the _docker/application/docker-compose-.yml files to start the dockers.