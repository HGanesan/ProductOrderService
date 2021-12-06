DROP DATABASE IF EXISTS `poi`;

CREATE DATABASE `poi`;

USE `poi`;

CREATE TABLE `poi`.`order_requests` (
  `id` INT AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) ,
  `last_name` VARCHAR(255) ,
  `productID` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `order_requests_pk` UNIQUE (`id`));