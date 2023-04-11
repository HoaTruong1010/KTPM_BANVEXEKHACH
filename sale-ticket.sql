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
INSERT INTO `customer` VALUES (1,'Jonny','03598741682'),(2,'Adam','03877469461'),(3,'Smith','0955478635'),(4,'Keilly','0966471234'),(5,'Siu','0177658368');
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
INSERT INTO `ticket` VALUES (1,'2','Empty',NULL,1,NULL,NULL),(2,'3','Empty',NULL,1,NULL,NULL),(3,'4','Empty',NULL,1,NULL,NULL),(4,'5','Empty',NULL,1,NULL,NULL),(5,'6','Empty',NULL,1,NULL,NULL),(6,'7','Empty',NULL,1,NULL,NULL),(7,'8','Empty',NULL,1,NULL,NULL),(8,'9','Empty',NULL,1,NULL,NULL),(9,'10','Empty',NULL,1,NULL,NULL),(10,'11','Empty',NULL,1,NULL,NULL),(11,'12','Empty',NULL,1,NULL,NULL),(12,'13','Empty',NULL,1,NULL,NULL),(13,'14','Empty',NULL,1,NULL,NULL),(14,'15','Empty',NULL,1,NULL,NULL),(15,'16','Empty',NULL,1,NULL,NULL),(16,'17','Empty',NULL,1,NULL,NULL),(17,'18','Empty',NULL,1,NULL,NULL),(18,'19','Empty',NULL,1,NULL,NULL),(19,'20','Empty',NULL,1,NULL,NULL),(273,'1','Empty',NULL,1,NULL,NULL),(274,'1','Empty',NULL,2,NULL,NULL),(275,'2','Empty',NULL,2,NULL,NULL),(276,'3','Empty',NULL,2,NULL,NULL),(277,'4','Empty',NULL,2,NULL,NULL),(278,'5','Empty',NULL,2,NULL,NULL),(279,'6','Empty',NULL,2,NULL,NULL),(280,'7','Empty',NULL,2,NULL,NULL),(281,'8','Empty',NULL,2,NULL,NULL),(282,'9','Empty',NULL,2,NULL,NULL),(283,'10','Empty',NULL,2,NULL,NULL),(284,'11','Empty',NULL,2,NULL,NULL),(285,'12','Empty',NULL,2,NULL,NULL),(286,'13','Empty',NULL,2,NULL,NULL),(287,'14','Empty',NULL,2,NULL,NULL),(288,'15','Empty',NULL,2,NULL,NULL),(289,'16','Empty',NULL,2,NULL,NULL),(290,'17','Empty',NULL,2,NULL,NULL),(291,'18','Empty',NULL,2,NULL,NULL),(292,'19','Empty',NULL,2,NULL,NULL),(293,'20','Empty',NULL,2,NULL,NULL),(294,'1','Empty',NULL,3,NULL,NULL),(295,'2','Empty',NULL,3,NULL,NULL),(296,'3','Empty',NULL,3,NULL,NULL),(297,'4','Empty',NULL,3,NULL,NULL),(298,'5','Empty',NULL,3,NULL,NULL),(299,'6','Empty',NULL,3,NULL,NULL),(300,'7','Empty',NULL,3,NULL,NULL),(301,'8','Empty',NULL,3,NULL,NULL),(302,'9','Empty',NULL,3,NULL,NULL),(303,'10','Empty',NULL,3,NULL,NULL),(304,'11','Empty',NULL,3,NULL,NULL),(305,'12','Empty',NULL,3,NULL,NULL),(306,'13','Empty',NULL,3,NULL,NULL),(307,'14','Empty',NULL,3,NULL,NULL),(308,'15','Empty',NULL,3,NULL,NULL),(309,'16','Empty',NULL,3,NULL,NULL),(310,'17','Empty',NULL,3,NULL,NULL),(311,'18','Empty',NULL,3,NULL,NULL),(312,'19','Empty',NULL,3,NULL,NULL),(313,'20','Empty',NULL,3,NULL,NULL),(314,'21','Empty',NULL,3,NULL,NULL),(315,'22','Empty',NULL,3,NULL,NULL),(316,'23','Empty',NULL,3,NULL,NULL),(317,'24','Empty',NULL,3,NULL,NULL),(318,'25','Empty',NULL,3,NULL,NULL),(319,'26','Empty',NULL,3,NULL,NULL),(320,'27','Empty',NULL,3,NULL,NULL),(321,'28','Empty',NULL,3,NULL,NULL),(322,'29','Empty',NULL,3,NULL,NULL),(323,'30','Empty',NULL,3,NULL,NULL),(324,'1','Empty',NULL,4,NULL,NULL),(325,'2','Empty',NULL,4,NULL,NULL),(326,'3','Empty',NULL,4,NULL,NULL),(327,'4','Empty',NULL,4,NULL,NULL),(328,'5','Empty',NULL,4,NULL,NULL),(329,'6','Empty',NULL,4,NULL,NULL),(330,'7','Empty',NULL,4,NULL,NULL),(331,'8','Empty',NULL,4,NULL,NULL),(332,'9','Empty',NULL,4,NULL,NULL),(333,'10','Empty',NULL,4,NULL,NULL),(334,'11','Empty',NULL,4,NULL,NULL),(335,'12','Empty',NULL,4,NULL,NULL),(336,'13','Empty',NULL,4,NULL,NULL),(337,'14','Empty',NULL,4,NULL,NULL),(338,'15','Empty',NULL,4,NULL,NULL),(339,'16','Empty',NULL,4,NULL,NULL),(340,'17','Empty',NULL,4,NULL,NULL),(341,'18','Empty',NULL,4,NULL,NULL),(342,'19','Empty',NULL,4,NULL,NULL),(343,'20','Empty',NULL,4,NULL,NULL),(344,'21','Empty',NULL,4,NULL,NULL),(345,'22','Empty',NULL,4,NULL,NULL),(346,'23','Empty',NULL,4,NULL,NULL),(347,'24','Empty',NULL,4,NULL,NULL),(348,'25','Empty',NULL,4,NULL,NULL),(349,'26','Empty',NULL,4,NULL,NULL),(350,'27','Empty',NULL,4,NULL,NULL),(351,'28','Empty',NULL,4,NULL,NULL),(352,'29','Empty',NULL,4,NULL,NULL),(353,'30','Empty',NULL,4,NULL,NULL),(354,'1','Empty',NULL,5,NULL,NULL),(355,'2','Empty',NULL,5,NULL,NULL),(356,'3','Empty',NULL,5,NULL,NULL),(357,'4','Empty',NULL,5,NULL,NULL),(358,'5','Empty',NULL,5,NULL,NULL),(359,'6','Empty',NULL,5,NULL,NULL),(360,'7','Empty',NULL,5,NULL,NULL),(361,'8','Empty',NULL,5,NULL,NULL),(362,'9','Empty',NULL,5,NULL,NULL),(363,'10','Empty',NULL,5,NULL,NULL),(364,'11','Empty',NULL,5,NULL,NULL),(365,'12','Empty',NULL,5,NULL,NULL),(366,'13','Empty',NULL,5,NULL,NULL),(367,'14','Empty',NULL,5,NULL,NULL),(368,'15','Empty',NULL,5,NULL,NULL),(369,'16','Empty',NULL,5,NULL,NULL),(370,'17','Empty',NULL,5,NULL,NULL),(371,'18','Empty',NULL,5,NULL,NULL),(372,'19','Empty',NULL,5,NULL,NULL),(373,'20','Empty',NULL,5,NULL,NULL),(374,'1','Empty',NULL,6,NULL,NULL),(375,'2','Empty',NULL,6,NULL,NULL),(376,'3','Empty',NULL,6,NULL,NULL),(377,'4','Empty',NULL,6,NULL,NULL),(378,'5','Empty',NULL,6,NULL,NULL),(379,'6','Empty',NULL,6,NULL,NULL),(380,'7','Empty',NULL,6,NULL,NULL),(381,'8','Empty',NULL,6,NULL,NULL),(382,'9','Empty',NULL,6,NULL,NULL),(383,'10','Empty',NULL,6,NULL,NULL),(384,'11','Empty',NULL,6,NULL,NULL),(385,'12','Empty',NULL,6,NULL,NULL),(386,'13','Empty',NULL,6,NULL,NULL),(387,'14','Empty',NULL,6,NULL,NULL),(388,'15','Empty',NULL,6,NULL,NULL),(389,'16','Empty',NULL,6,NULL,NULL),(390,'17','Empty',NULL,6,NULL,NULL),(391,'18','Empty',NULL,6,NULL,NULL),(392,'19','Empty',NULL,6,NULL,NULL),(393,'20','Empty',NULL,6,NULL,NULL);
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
INSERT INTO `trip` VALUES (1,'2023-04-28 01:00:00','2023-04-28 04:45:00',150,1,1),(2,'2023-04-28 05:45:00','2023-04-28 09:15:00',150,1,2),(3,'2023-04-28 01:00:00','2023-04-28 02:00:00',120,2,3),(4,'2023-04-28 03:00:00','2023-04-28 04:00:00',120,2,4),(5,'2023-04-28 01:15:00','2023-04-28 05:45:00',400,3,5),(6,'2023-04-28 06:45:00','2023-04-28 11:00:00',400,3,6);
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
INSERT INTO `user` VALUES (1,'u1','1','Admin','Truong Thi Kim Hoa'),(2,'u2','1','Employee','Tran Nguyen Hong An'),(3,'u3','1','Employee','Nguyen Van Hau'),(4,'u4','1','Employee','Quach Phu Hao');
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

-- Dump completed on 2023-04-11 20:42:23
