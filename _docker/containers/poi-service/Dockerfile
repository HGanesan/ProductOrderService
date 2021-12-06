FROM centos:7

# nginx
RUN yum -y install epel-release

# Install wget
RUN yum -y install wget

# Install JDK 8
RUN yum -y install java-1.8.0-openjdk

# Install mysql client 5.7
RUN wget http://dev.mysql.com/get/mysql57-community-release-el7-7.noarch.rpm
RUN yum -y localinstall mysql57-community-release-el7-7.noarch.rpm
RUN yum -y install mysql

RUN mkdir /application

WORKDIR /application

ENTRYPOINT ["java","-jar","poi-service.jar"]

