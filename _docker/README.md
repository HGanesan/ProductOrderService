# DEVELOPEMENT ENVIRONMENT
### Build all projects

	$ mvn -f ../pom.xml clean package


### Starting develop environment for the first time

    $ docker-compose -f application/docker-compose-example.yml up -d --build


### Stop develop environment

    $ docker-compose -f application/docker-compose-example.yml stop


### Starting develop environment without force building

    $ docker-compose -f application/docker-compose-example.yml up -d

	

# INTEGRATION TEST (IT) ENVIRONMENT
### Build all projects

	$ mvn -f ../pom.xml clean package


### Starting IT environment

    $ docker-compose -f application/docker-compose-it.yml up -d --build --renew-anon-volumes


### Stop integration-test environment

    $ docker-compose -f application/docker-compose-it.yml 


# TIPS & TRICKS
### Start only a docker with the database for IT environment

	$ docker-compose -f application/docker-compose-it.yml up -d --build --renew-anon-volumes --no-deps database


