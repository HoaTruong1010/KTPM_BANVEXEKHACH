-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: sale-ticket
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lisense_plate` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sum_chair` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (1,'93A-11232','XeA',20),(2,'93A-11236','XeB',30),(3,'93A-11234','XeC',20),(4,'93B-19102','XeD',20),(5,'93H-19668','xeF',30);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Jonny','0359874164'),(2,'Adam','0387746946'),(3,'Smith','0955478635'),(4,'Keilly','0966471234'),(5,'Siu','0177658368');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `end` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,'Vi Thanh','Ho Chi Minh'),(2,'Ho Chi Minh','Vi Thanh'),(3,'Dong Nai','Ho Chi Minh'),(4,'Ho Chi Minh','Dong Nai'),(5,'Da Lat','Ho Chi Minh'),(6,'Ho Chi Minh','Da Lat');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chair` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `print_date` datetime DEFAULT NULL,
  `trip_id` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk1_trip_ticket` (`trip_id`),
  KEY `fk2_customer_ticket` (`customer_id`) /*!80000 INVISIBLE */,
  KEY `fk3_user_ticket_idx` (`user_id`),
  CONSTRAINT `fk1_trip_ticket` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk2_customer_ticket` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk3_user_ticket` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'1','Reserved',NULL,1,2,NULL),(2,'2','Reserved',NULL,1,2,NULL),(3,'3','Reserved',NULL,1,2,NULL),(4,'4','Empty',NULL,1,NULL,NULL),(5,'5','Empty',NULL,1,NULL,NULL),(6,'6','Empty',NULL,1,NULL,NULL),(7,'7','Empty',NULL,1,NULL,NULL),(8,'8','Empty',NULL,1,NULL,NULL),(9,'9','Empty',NULL,1,NULL,NULL),(10,'10','Empty',NULL,1,NULL,NULL),(11,'11','Empty',NULL,1,NULL,NULL),(12,'12','Empty',NULL,1,NULL,NULL),(13,'13','Empty',NULL,1,NULL,NULL),(14,'14','Empty',NULL,1,NULL,NULL),(15,'15','Empty',NULL,1,NULL,NULL),(16,'16','Empty',NULL,1,NULL,NULL),(17,'17','Empty',NULL,1,NULL,NULL),(18,'18','Empty',NULL,1,NULL,NULL),(19,'19','Empty',NULL,1,NULL,NULL),(20,'20','Empty',NULL,1,NULL,NULL),(21,'1','Sold','2023-04-18 11:21:42',2,5,1),(22,'2','Empty',NULL,2,NULL,NULL),(23,'3','Sold','2023-04-18 11:21:42',2,5,1),(24,'4','Empty',NULL,2,NULL,NULL),(25,'5','Empty',NULL,2,NULL,NULL),(26,'6','Empty',NULL,2,NULL,NULL),(27,'7','Empty',NULL,2,NULL,NULL),(28,'8','Empty',NULL,2,NULL,NULL),(29,'9','Empty',NULL,2,NULL,NULL),(30,'10','Empty',NULL,2,NULL,NULL),(31,'11','Empty',NULL,2,NULL,NULL),(32,'12','Empty',NULL,2,NULL,NULL),(33,'13','Empty',NULL,2,NULL,NULL),(34,'14','Empty',NULL,2,NULL,NULL),(35,'15','Empty',NULL,2,NULL,NULL),(36,'16','Empty',NULL,2,NULL,NULL),(37,'17','Empty',NULL,2,NULL,NULL),(38,'18','Empty',NULL,2,NULL,NULL),(39,'19','Empty',NULL,2,NULL,NULL),(40,'20','Sold','2023-04-18 11:21:42',2,5,1),(41,'1','Reserved',NULL,3,NULL,NULL),(42,'2','Reserved',NULL,3,NULL,NULL),(43,'3','Empty',NULL,3,NULL,NULL),(44,'4','Empty',NULL,3,NULL,NULL),(45,'5','Empty',NULL,3,NULL,NULL),(46,'6','Empty',NULL,3,NULL,NULL),(47,'7','Empty',NULL,3,NULL,NULL),(48,'8','Empty',NULL,3,NULL,NULL),(49,'9','Empty',NULL,3,NULL,NULL),(50,'10','Empty',NULL,3,NULL,NULL),(51,'11','Empty',NULL,3,NULL,NULL),(52,'12','Empty',NULL,3,NULL,NULL),(53,'13','Empty',NULL,3,NULL,NULL),(54,'14','Empty',NULL,3,NULL,NULL),(55,'15','Empty',NULL,3,NULL,NULL),(56,'16','Empty',NULL,3,NULL,NULL),(57,'17','Empty',NULL,3,NULL,NULL),(58,'18','Empty',NULL,3,NULL,NULL),(59,'19','Empty',NULL,3,NULL,NULL),(60,'20','Empty',NULL,3,NULL,NULL),(61,'21','Empty',NULL,3,NULL,NULL),(62,'22','Empty',NULL,3,NULL,NULL),(63,'23','Empty',NULL,3,NULL,NULL),(64,'24','Empty',NULL,3,NULL,NULL),(65,'25','Empty',NULL,3,NULL,NULL),(66,'26','Empty',NULL,3,NULL,NULL),(67,'27','Empty',NULL,3,NULL,NULL),(68,'28','Empty',NULL,3,NULL,NULL),(69,'29','Empty',NULL,3,NULL,NULL),(70,'30','Empty',NULL,3,NULL,NULL),(71,'1','Empty',NULL,4,NULL,NULL),(72,'2','Empty',NULL,4,NULL,NULL),(73,'3','Empty',NULL,4,NULL,NULL),(74,'4','Empty',NULL,4,NULL,NULL),(75,'5','Empty',NULL,4,NULL,NULL),(76,'6','Empty',NULL,4,NULL,NULL),(77,'7','Empty',NULL,4,NULL,NULL),(78,'8','Empty',NULL,4,NULL,NULL),(79,'9','Empty',NULL,4,NULL,NULL),(80,'10','Empty',NULL,4,NULL,NULL),(81,'11','Empty',NULL,4,NULL,NULL),(82,'12','Empty',NULL,4,NULL,NULL),(83,'13','Empty',NULL,4,NULL,NULL),(84,'14','Empty',NULL,4,NULL,NULL),(85,'15','Empty',NULL,4,NULL,NULL),(86,'16','Empty',NULL,4,NULL,NULL),(87,'17','Empty',NULL,4,NULL,NULL),(88,'18','Empty',NULL,4,NULL,NULL),(89,'19','Empty',NULL,4,NULL,NULL),(90,'20','Empty',NULL,4,NULL,NULL),(91,'21','Empty',NULL,4,NULL,NULL),(92,'22','Empty',NULL,4,NULL,NULL),(93,'23','Empty',NULL,4,NULL,NULL),(94,'24','Empty',NULL,4,NULL,NULL),(95,'25','Empty',NULL,4,NULL,NULL),(96,'26','Empty',NULL,4,NULL,NULL),(97,'27','Empty',NULL,4,NULL,NULL),(98,'28','Empty',NULL,4,NULL,NULL),(99,'29','Empty',NULL,4,NULL,NULL),(100,'30','Empty',NULL,4,NULL,NULL),(101,'1','Empty',NULL,5,NULL,NULL),(102,'2','Empty',NULL,5,NULL,NULL),(103,'3','Empty',NULL,5,NULL,NULL),(104,'4','Empty',NULL,5,NULL,NULL),(105,'5','Empty',NULL,5,NULL,NULL),(106,'6','Empty',NULL,5,NULL,NULL),(107,'7','Empty',NULL,5,NULL,NULL),(108,'8','Empty',NULL,5,NULL,NULL),(109,'9','Empty',NULL,5,NULL,NULL),(110,'10','Empty',NULL,5,NULL,NULL),(111,'11','Empty',NULL,5,NULL,NULL),(112,'12','Empty',NULL,5,NULL,NULL),(113,'13','Empty',NULL,5,NULL,NULL),(114,'14','Empty',NULL,5,NULL,NULL),(115,'15','Empty',NULL,5,NULL,NULL),(116,'16','Empty',NULL,5,NULL,NULL),(117,'17','Empty',NULL,5,NULL,NULL),(118,'18','Empty',NULL,5,NULL,NULL),(119,'19','Empty',NULL,5,NULL,NULL),(120,'20','Empty',NULL,5,NULL,NULL);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `departing_at` datetime NOT NULL,
  `arriving_at` datetime NOT NULL,
  `price` double NOT NULL,
  `car_id` int NOT NULL,
  `route_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_car_trip` (`car_id`),
  KEY `fk_route_trip` (`route_id`),
  CONSTRAINT `fk_car_trip` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_route_trip` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
INSERT INTO `trip` VALUES (1,'2023-04-28 01:00:00','2023-04-28 04:45:00',150,1,1),(2,'2023-04-28 05:45:00','2023-04-28 09:15:00',150,1,2),(3,'2023-04-28 01:00:00','2023-04-28 02:00:00',120,2,3),(4,'2023-04-28 03:00:00','2023-04-28 04:00:00',120,2,4),(5,'2023-04-29 06:45:00','2023-04-29 10:45:00',150,1,5);
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_role` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'u1','c92c0babdc764d8674bcea14a55d867d','Admin','Truong Thi Kim Hoa'),(2,'u2','c92c0babdc764d8674bcea14a55d867d','Employee','Tran Nguyen Hong An'),(3,'u3','c92c0babdc764d8674bcea14a55d867d','Employee','Nguyen Van Hau'),(4,'u4','c92c0babdc764d8674bcea14a55d867d','Employee','Quach Phu Hao');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-18 11:38:40
