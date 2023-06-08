CREATE TABLE IF NOT EXISTS `contact_msg` (
 `contact_id` int AUTO_INCREMENT  PRIMARY KEY,
 `name` varchar(100) NOT NULL,
 `mobile_num` varchar(10) NOT NULL,
 `email` varchar(100) NOT NULL,
 `message` varchar(500) NOT NULL,
 `status` varchar(10) NOT NULL,
 `created_at` TIMESTAMP NOT NULL,
 `created_by` varchar(50) NOT NULL,
 `updated_at` TIMESTAMP DEFAULT NULL,
 `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `roles` (
 `role_id` int NOT NULL AUTO_INCREMENT,
 `role_name` varchar(50) NOT NULL,
 `created_at` TIMESTAMP NOT NULL,
 `created_by` varchar(50) NOT NULL,
 `updated_at` TIMESTAMP DEFAULT NULL,
 `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);


CREATE TABLE IF NOT EXISTS `address` (
 `address_id` int NOT NULL AUTO_INCREMENT,
 `address1` varchar(200) NOT NULL,
 `created_at` TIMESTAMP NOT NULL,
 `created_by` varchar(50) NOT NULL,
 `updated_at` TIMESTAMP DEFAULT NULL,
 `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`address_id`)
);


CREATE TABLE IF NOT EXISTS `person` (
 `person_id` int NOT NULL AUTO_INCREMENT,
 `name` varchar(100) NOT NULL,
 `email` varchar(50) NOT NULL,
 `mobile_number` varchar(20) NOT NULL,
 `pwd` varchar(200) NOT NULL,
 `role_id` int NOT NULL,
 `address_id` int NULL,
 `created_at` TIMESTAMP NOT NULL,
 `created_by` varchar(50) NOT NULL,
 `updated_at` TIMESTAMP DEFAULT NULL,
 `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  FOREIGN KEY (role_id) REFERENCES roles(role_id),
  FOREIGN KEY (address_id) REFERENCES address(address_id)
);

CREATE TABLE IF NOT EXISTS `service` (
 `service_id` int NOT NULL AUTO_INCREMENT,
 `name` varchar(100) NOT NULL,
 `created_at` TIMESTAMP NOT NULL,
 `created_by` varchar(50) NOT NULL,
 `updated_at` TIMESTAMP DEFAULT NULL,
 `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
); (class)

ALTER TABLE `person`
ADD COLUMN `service_id` int NULL AFTER `address_id`,
ADD CONSTRAINT `FK_CLASS_CLASS_ID` FOREIGN KEY (`service_id`)
REFERENCES `service`(`service_id`);


CREATE TABLE IF NOT EXISTS `product` (
 `product_id` int NOT NULL AUTO_INCREMENT,
 `name` varchar(100) NOT NULL,
 `fees` int NOT NULL,
 `image` longblob NOT NULL,
 `created_at` TIMESTAMP NOT NULL,
 `created_by` varchar(50) NOT NULL,
 `updated_at` TIMESTAMP DEFAULT NULL,
 `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE IF NOT EXISTS `person_product` (
 `person_id` int NOT NULL,
 `product_id` int NOT NULL,
 FOREIGN KEY (person_id) REFERENCES person(person_id),
 FOREIGN KEY (product_id) REFERENCES product(product_id),
  PRIMARY KEY (`person_id`,`product_id`)
);


CREATE TABLE IF NOT EXISTS `subscribe` (
 `subscribe_id` int AUTO_INCREMENT  PRIMARY KEY,
 `email` varchar(100) NOT NULL
);

create table ORDERS
(
 ID               int AUTO_INCREMENT primary key,
 PRICE            int not null,
 CORRENCY         varchar(200) not null,
 METHOD           varchar(100) not null,
 INTENT           varchar(255) not null,
 DESCRIPTION      varchar(255) null,
 CUSTOMER_ADDRESS VARCHAR(255) null,
 CUSTOMER_EMAIL   VARCHAR(128) not null,
 CUSTOMER_NAME    VARCHAR(255) not null,
 CUSTOMER_PHONE   VARCHAR(128) null,
 created_at       TIMESTAMP not null,
 created_by varchar(50) NOT NULL
 ORDER_NUM        INTEGER not null
) ;
alter table ORDERS
 add constraint ORDER_UK unique (ORDER_NUM) ;


create table ORDER_DETAILS
(
 ID         int auto_increment primary key,
 AMOUNT     int not null,
 PRICE      int not null,
 QUANITY    INTEGER not null,
 ORDER_ID   int not null,
 PRODUCT_ID int not null

) ;
ALTER TABLE ORDER_DETAILS
ADD CONSTRAINT ORDER_DETAIL_ORD_FK FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID);

ALTER TABLE ORDER_DETAILS
ADD CONSTRAINT ORDER_DETAIL_PROD_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (product_id);