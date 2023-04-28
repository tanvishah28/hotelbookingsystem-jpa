CREATE DATABASE  IF NOT EXISTS `hoteldb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `hoteldb`;
-- MySQL dump 10.13  Distrib 5.6.50, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hoteldb
-- ------------------------------------------------------
-- Server version	5.6.50-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_number` int(11) DEFAULT NULL,
  `bill_amt` decimal(8,2) DEFAULT NULL,
  `bill_DateTime` datetime DEFAULT NULL,
  `bill_status` varchar(50) DEFAULT NULL,
  `payment_method` varchar(10) DEFAULT NULL,
  `discount` varchar(5) DEFAULT NULL,
  `bill_reward_points` decimal(10,2) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=708 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (701,1001,12000.00,'2022-02-26 11:15:00','PAID','DEBIT','10%',20.00,301),(702,1002,17000.00,'2022-02-26 11:15:00','PARTIALLY_PAID','CREDIT','0',0.00,302),(703,1003,20000.00,'2022-02-27 11:15:00','PAID','DEBIT','10%',200.00,303),(704,1004,12000.00,'2022-03-12 11:15:00','PAID','CREDIT','5%',80.00,304),(705,1005,20000.00,'2022-03-13 11:15:00','PAID','CASH','20%',200.00,303),(706,1006,15000.00,'2022-06-20 11:15:00','PAID','DEBIT','5%',70.00,305),(707,1007,18000.00,'2022-06-25 11:15:00','PAID','CASH','15%',150.00,305);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_dateTime` datetime DEFAULT NULL,
  `booking_amt` decimal(10,2) DEFAULT NULL,
  `check_in_DateTime` datetime DEFAULT NULL,
  `check_out_DateTime` datetime DEFAULT NULL,
  `no_of_adults` int(11) DEFAULT NULL,
  `no_of_child` int(11) DEFAULT NULL,
  `booking_status` varchar(20) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `bill_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `hotel_id` (`hotel_id`),
  KEY `customer_id` (`customer_id`),
  KEY `bill_id` (`bill_id`),
  KEY `room_id` (`room_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`) ON DELETE CASCADE,
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`) ON DELETE CASCADE,
  CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE,
  CONSTRAINT `booking_ibfk_5` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (501,'2022-02-22 08:05:22',5000.00,'2022-02-25 12:05:22','2022-02-26 11:05:22',2,NULL,'COMPLETED',101,301,701,201,401),(502,'2022-02-20 12:05:22',5500.00,'2022-02-25 12:05:22','2022-02-26 11:05:22',1,1,'POSTPONED',101,302,702,202,401),(503,'2022-02-25 06:05:22',8000.00,NULL,NULL,2,1,'NO_SHOW',102,303,703,204,402),(504,'2022-02-28 06:05:22',5000.00,'2022-03-11 12:05:22','2022-03-12 11:05:22',2,NULL,'COMPLETED',102,304,704,205,402),(505,'2022-03-01 06:05:22',8100.00,'2022-03-12 12:05:22','2022-03-13 11:05:22',2,1,'COMPLETED',103,303,705,206,403),(506,'2022-06-10 06:05:22',5000.00,'2022-06-15 12:05:22','2022-06-20 11:05:22',2,1,'COMPLETED',101,305,706,201,401),(507,'2022-06-20 06:05:22',9000.00,'2022-06-24 12:05:22','2022-06-25 11:05:22',2,2,'COMPLETED',102,305,707,205,402);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_service`
--

DROP TABLE IF EXISTS `booking_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_service` (
  `booking_service_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `hotel_service_id` int(11) DEFAULT NULL,
  `rating` decimal(3,1) DEFAULT NULL,
  PRIMARY KEY (`booking_service_id`),
  KEY `booking_id` (`booking_id`),
  KEY `hotel_service_id` (`hotel_service_id`),
  CONSTRAINT `booking_service_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE,
  CONSTRAINT `booking_service_ibfk_2` FOREIGN KEY (`hotel_service_id`) REFERENCES `hotel_service` (`hotel_service_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_service`
--

LOCK TABLES `booking_service` WRITE;
/*!40000 ALTER TABLE `booking_service` DISABLE KEYS */;
INSERT INTO `booking_service` VALUES (1,501,1,5.0),(2,501,2,3.0),(3,501,3,4.0),(4,504,4,4.0),(5,504,6,5.0),(6,505,2,3.0);
/*!40000 ALTER TABLE `booking_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(20) DEFAULT NULL,
  `customer_address` varchar(100) DEFAULT NULL,
  `customer_phone` varchar(10) DEFAULT NULL,
  `customer_email` varchar(50) DEFAULT NULL,
  `preferences` varchar(50) DEFAULT NULL,
  `special_needs` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (301,'John','Delhi','9876543810','john@gmail.com','King Size Bed',NULL),(302,'Mary','Bangalore','9876543212','mary@gmail.com',NULL,'wheelchair'),(303,'Emily','Nainital','9876543540','emily@gmail.com','King Size Bed',NULL),(304,'Jack','Pune','9876543217','jack@gmail.com','Single Bed',NULL),(305,'Lily','Dehradun','9876543222','lily@gmail.com',NULL,NULL),(306,'Sid','New York','4443334444','sid@gmail.com','King Size Bed','Vegan Food');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(20) DEFAULT NULL,
  `emp_salary` decimal(10,2) DEFAULT NULL,
  `emp_phone` varchar(10) DEFAULT NULL,
  `emp_address` varchar(20) DEFAULT NULL,
  `emp_email` varchar(50) DEFAULT NULL,
  `joining_date` date DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  KEY `hotel_id` (`hotel_id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (401,'Peter',25000.00,'9876543310','Chennai','peter@gmail.com','2022-02-21',101),(402,'Lisa',35000.00,'9876543211','Delhi','lisa@gmail.com','2022-02-22',102),(403,'Emma',50000.00,'9876543213','Pune','emma@gmail.com','2022-02-21',103),(404,'Chad',40000.00,'9876521380','Mumbai','chad@gmail.com','2022-02-21',104);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotel` (
  `hotel_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(20) DEFAULT NULL,
  `hotel_location` varchar(20) DEFAULT NULL,
  `hotel_phone` varchar(10) DEFAULT NULL,
  `hotel_email` varchar(50) DEFAULT NULL,
  `hotel_rating` int(11) DEFAULT NULL,
  `pet_friendly` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (101,'Taj','Mumbai','9876543210','taj@taj.com',5,1),(102,'Hyatt','Bangalore','9876543212','hyatt@hyatt.com',4,0),(103,'Leela','Mumbai','9876543202','leela@leela.com',5,1),(104,'Oberio','Mumbai','9874562130','oberio@oberio.com',5,0),(105,'Taj Mahal','Agra','9876543210','tajmahal@taj.com',5,1),(106,' Grand Hyatt','Bangalore','9876543212','grandhyatt@hyatt.com',4,0),(107,'ITC','Mumbai','9876543202','itc@leela.com',5,1),(108,'Raddison','Mumbai','9874562130','raddison@oberio.com',5,0),(109,'Lake View','Saputara','7890983347','lakeview@gmail.com',5,1);
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_customer`
--

DROP TABLE IF EXISTS `hotel_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotel_customer` (
  `hotel_customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `customer_type` varchar(15) DEFAULT NULL,
  `customer_reward_points` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`hotel_customer_id`),
  KEY `hotel_id` (`hotel_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `hotel_customer_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`) ON DELETE CASCADE,
  CONSTRAINT `hotel_customer_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_customer`
--

LOCK TABLES `hotel_customer` WRITE;
/*!40000 ALTER TABLE `hotel_customer` DISABLE KEYS */;
INSERT INTO `hotel_customer` VALUES (1,101,301,'Platinum',200.00),(2,101,302,'Gold',100.00),(3,101,303,'Silver',50.00),(4,102,303,'Gold',100.00),(5,103,303,'Platinum',200.00),(6,102,304,'Silver',50.00),(7,101,305,'Platinum',200.00),(8,102,305,'Silver',50.00);
/*!40000 ALTER TABLE `hotel_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_service`
--

DROP TABLE IF EXISTS `hotel_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotel_service` (
  `hotel_service_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `service_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`hotel_service_id`),
  KEY `hotel_id` (`hotel_id`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `hotel_service_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`) ON DELETE CASCADE,
  CONSTRAINT `hotel_service_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_service`
--

LOCK TABLES `hotel_service` WRITE;
/*!40000 ALTER TABLE `hotel_service` DISABLE KEYS */;
INSERT INTO `hotel_service` VALUES (1,101,601,1000.00),(2,101,602,1500.00),(3,101,603,5000.00),(4,102,601,1200.00),(5,102,602,1500.00),(6,102,603,6000.00),(7,101,604,2000.00),(8,103,601,1000.00),(9,103,602,1500.00),(10,104,601,1000.00),(11,104,602,1500.00);
/*!40000 ALTER TABLE `hotel_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_no` int(11) DEFAULT NULL,
  `room_type` varchar(50) DEFAULT NULL,
  `room_price` decimal(10,2) DEFAULT NULL,
  `floor_no` int(11) DEFAULT NULL,
  `area_of_room` varchar(15) DEFAULT NULL,
  `occupancy_details` varchar(30) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `hotel_id` (`hotel_id`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (201,1,'Garden View',10000.00,11,'1500 sq.ft.','2 adults',101),(202,2,'Sea View',15000.00,12,'1800 sq.ft.','2 adults, 2 child',101),(203,3,'Suite Room',18000.00,14,'2000 sq.ft.','2 adults, 2 child',101),(204,4,'Garden View',10000.00,11,'1500 sq.ft.','2 adults',102),(205,5,'Sea View',15000.00,12,'1800 sq.ft.','2 adults, 2 child',102),(206,6,'Suite Room',18000.00,14,'2000 sq.ft.','2 adults, 2 child',103),(207,7,'Suite Room',18000.00,14,'2000 sq.ft.','2 adults, 2 child',103);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=605 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (601,'Laundry'),(602,'Cab service'),(603,'Personal chef'),(604,'Room Service');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-08 12:12:32
